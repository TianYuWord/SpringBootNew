package cn.boot.EnableAutoConfiguration.importSelector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"cn.boot.EnableAutoConfiguration.model.RoleBean","cn.boot.EnableAutoConfiguration.model.UserBean"};
    }
}
