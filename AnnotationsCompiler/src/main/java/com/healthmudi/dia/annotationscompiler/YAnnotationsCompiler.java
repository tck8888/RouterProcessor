package com.healthmudi.dia.annotationscompiler;

import com.google.auto.service.AutoService;
import com.healthmudi.dia.routerannotations.YRouter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;

/**
 * <p>description:</p>
 * <p>created on: 2019/5/21 8:32</p>
 *
 * @author tck
 * @version 3.5
 */
@AutoService(Processor.class)
public class YAnnotationsCompiler extends AbstractProcessor {

    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();


    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(YRouter.class);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            YRouter annotation = typeElement.getAnnotation(YRouter.class);
            String value = annotation.value();

            String string = typeElement.getQualifiedName().toString();

            stringStringHashMap.put(value, string);
        }

        if (stringStringHashMap.size() > 0) {
            Writer writer = null;
            try {
                JavaFileObject sourceFile = filer.createSourceFile("com.healthmudi.dia.router." + "Activity" + System.currentTimeMillis());
                writer = sourceFile.openWriter();
               // writer.write();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return false;
    }

    //声明注解要处理的注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> objects = new HashSet<>();
        objects.add(YRouter.class.getCanonicalName());
        return objects;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

}
