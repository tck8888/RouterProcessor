package com.tck.trouter.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tck.trouter.annotations.TRouter;
import com.tck.trouter.compiler.utils.ProcessorConfig;

import java.util.Set;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * description:
 *
 * @author tck88
 * @version v1.0.0
 * @date 2020/12/13 19:21
 */
// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)
// 允许/支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes({ProcessorConfig.AROUTER_PACKAGE})
// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)

// 注解处理器接收的参数
@SupportedOptions({ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})
public class TRouterProcessor extends AbstractProcessor {

    // 操作Element的工具类（类，函数，属性，其实都是Element）
    private Elements elementTool;

    // type(类信息)的工具类，包含用于操作TypeMirror的工具方法
    private Types typeTool;

    // Message用来打印 日志相关信息
    private Messager messager;

    // 文件生成器， 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private Filer filer;

    private String options;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementTool = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

        // 只有接受到 App壳 传递过来的书籍，才能证明我们的 APT环境搭建完成
        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        String aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> options:" + options);
        messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> aptPackage:" + aptPackage);
        if (options != null && aptPackage != null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境搭建完成....");
        } else {
            messager.printMessage(Diagnostic.Kind.NOTE, "APT 环境有问题，请检查 options 与 aptPackage 为null...");
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "并没有发现 被@TRouter 注解的地方呀");
            return false;
        }
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TRouter.class);
        for (Element element : elements) {
            // 获取类节点，获取包节点
            String packageName = elementTool.getPackageOf(element).getQualifiedName().toString();
            // 获取简单类名，例如：MainActivity
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>> 被@TRouter 注解的类有：" + className); // 打印出 就证明APT没有问题


            TRouter tRouter = element.getAnnotation(TRouter.class);

            // 方法
            MethodSpec mainMethod = MethodSpec.methodBuilder("findTargetClass")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(Class.class)
                    .addParameter(String.class, "path")
                    // 增加main方法里面的内容
                    .addStatement("return path.equals($S)?$T.class:null", tRouter.path(), ClassName.get((TypeElement) element))
                    .build();
            // 类
            String finalClassName = className + "$TRouter";
            TypeSpec testClass = TypeSpec.classBuilder(finalClassName)
                    .addMethod(mainMethod)
                    .addModifiers(Modifier.PUBLIC)
                    .build();

            // 包
            JavaFile packagef = JavaFile.builder(packageName, testClass).build();


            try {
                packagef.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "生成Test文件时失败，异常:" + e.getMessage());
            }

        }
        return true;
    }
}
