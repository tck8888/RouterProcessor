package com.healthmudi.dia.annotationscompiler;

import com.google.auto.service.AutoService;
import com.healthmudi.dia.routerannotations.YRouterPath;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/21 8:32</p>
 *
 * @author tck
 * @version 3.5
 */
@AutoService(Processor.class)
public class YRouterAnnotationsCompiler extends AbstractProcessor {

    private Filer filer;
    private final String KEY_MODULE_NAME = "moduleName";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        String moduleName = "";
        Map<String, String> options = processingEnv.getOptions();
        if (YUtils.isNotEmpty(options)) {
            moduleName = options.get(KEY_MODULE_NAME);
        }

        //所有YRouter的路由
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(YRouterPath.class);
        Map<String, String> map = new HashMap<>();
        if (elementsAnnotatedWith != null && elementsAnnotatedWith.size() > 0) {
            for (Element element1 : elementsAnnotatedWith) {
                TypeElement element = (TypeElement) element1;
                YRouterPath annotation = element.getAnnotation(YRouterPath.class);
                String value = annotation.value();
                //获取全类名
                String activityName = element.getQualifiedName().toString();
                map.put(value, activityName);
            }
        }

        //参数类型
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(
                        ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get("android.app", "Activity"))));
        //参数
        ParameterSpec routers = ParameterSpec.builder(parameterizedTypeName, "routers").build();
        //方法
        MethodSpec.Builder onLoad = MethodSpec.methodBuilder("onLoad")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(routers);

        if (YUtils.isNotEmpty(map)) {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> next : entries) {
                String key = next.getKey();
                String value = next.getValue();
                ClassName className = ClassName.bestGuess(value);
                onLoad.addStatement("routers.put($S,$T.class)", key, className);
            }
        }
        //要生成的类
        TypeSpec typeSpec = TypeSpec.classBuilder("YRouterPath$$Module$$" + moduleName)
                .addMethod(onLoad.build())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClassName.get(Constant.Y_ROUTER_INTERFACE_PACKAGE_NAME, Constant.Y_ROUTER_INTERFACE_CLASSNAME))
                .build();
        try {
            JavaFile.builder(Constant.GENERATE_ROUTERS_LOCATION, typeSpec)
                    .addFileComment("YRouterPath 自动生成")
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> objects = new HashSet<>();
        objects.add(YRouterPath.class.getCanonicalName());
        return objects;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
