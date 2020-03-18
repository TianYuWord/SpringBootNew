### һ �� ����Ŀ�꣺
1��������spring ��ImportSelector��ԭ�� �� spring �� spring.factories �ļ�����������ģ�
2��@EnableAutoConfiguration ���ͨ�� spring.factories ��ʵ��bean��ע��
3���ֶ�������ϰ���Զ���һ��spring.factories�ļ�

### ��������Spring ��ImportSelector��spring factoriesԭ��
�ҵ�SpringBoot��selectImports����
```
public Iterable<Entry> selectImports() {
    if (this.autoConfigurationEntries.isEmpty()) {
	return Collections.emptyList();
    } else {
	Set<String> allExclusions = (Set)this.autoConfigurationEntries.stream().map(AutoConfigurationImportSelector.AutoConfigurationEntry::getExclusions).flatMap(Collection::stream).collect(Collectors.toSet());
	Set<String> processedConfigurations = (Set)this.autoConfigurationEntries.stream().map(AutoConfigurationImportSelector.AutoConfigurationEntry::getConfigurations).flatMap(Collection::stream).collect(Collectors.toCollection(LinkedHashSet::new));
	processedConfigurations.removeAll(allExclusions);
	return (Iterable)this.sortAutoConfigurations(processedConfigurations, this.getAutoConfigurationMetadata()).stream().map((importClassName) -> {
	    return new Entry((AnnotationMetadata)this.entries.get(importClassName), importClassName);
	}).collect(Collectors.toList());
    }
}
```
�ص����
Set<String> processedConfigurations = (Set)this.autoConfigurationEntries.stream()
	.map(AutoConfigurationImportSelector.AutoConfigurationEntry::getConfigurations)
	.flatMap(Collection::stream)
	.collect(Collectors.toCollection(LinkedHashSet::new));
�ȿ� this.autoConfigurationEntries ����ô���ģ�����һ��
private final List<AutoConfigurationImportSelector.AutoConfigurationEntry> autoConfigurationEntries = new ArrayList();

˼�����⣺
autoConfigurationEntries ����ô��ֵ�ģ�
��process() ���������ҵ��˴�
```
public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
    Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector, () -> {
	return String.format("Only %s implementations are supported, got %s", AutoConfigurationImportSelector.class.getSimpleName(), deferredImportSelector.getClass().getName());
    });
    //�ֻ�Ҫע�ᵽ SpringIOC �� class
    AutoConfigurationImportSelector.AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector)deferredImportSelector).getAutoConfigurationEntry(this.getAutoConfigurationMetadata(), annotationMetadata);
    //���ռ�����class���autoConfigurationEntries����ȥ
    this.autoConfigurationEntries.add(autoConfigurationEntry);
    Iterator var4 = autoConfigurationEntry.getConfigurations().iterator();

    while(var4.hasNext()) {
	String importClassName = (String)var4.next();
	this.entries.putIfAbsent(importClassName, annotationMetadata);
    }

}
```
�������� ���Ǽ�����getAutoConfigurationEntry(this.getAutoConfigurationMetadata(), annotationMetadata);�����������ô����
```
protected AutoConfigurationImportSelector.AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata, AnnotationMetadata annotationMetadata) {
if (!this.isEnabled(annotationMetadata)) {
    return EMPTY_ENTRY;
} else {
    AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
    //�ռ���Ҫע���SpringIOC��class
    List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
    configurations = this.removeDuplicates(configurations);
    Set<String> exclusions = this.getExclusions(annotationMetadata, attributes);
    this.checkExcludedClasses(configurations, exclusions);
    configurations.removeAll(exclusions);
    configurations = this.filter(configurations, autoConfigurationMetadata);
    this.fireAutoConfigurationImportEvents(configurations, exclusions);
    return new AutoConfigurationImportSelector.AutoConfigurationEntry(configurations, exclusions);
}
}
```
������ getCandidateConfigurations(annotationMetadata, attributes); ���룬Դ������
```
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());
Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
return configurations;
}
```
�������� SpringFactoriesLoader.loadFactoryNames
```
public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
String factoryTypeName = factoryType.getName();
return (List)loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
}
```
�������� loadSpringFactories(classLoader)
```
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
MultiValueMap<String, String> result = (MultiValueMap)cache.get(classLoader);
if (result != null) {
    return result;
} else {
    try {
    //��Ҫ���֣���ȥ���������ļ�FACTORIES_RESOURCE_LOCATION
	Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
	LinkedMultiValueMap result = new LinkedMultiValueMap();

	while(urls.hasMoreElements()) {
	    URL url = (URL)urls.nextElement();
	    UrlResource resource = new UrlResource(url);
	    Properties properties = PropertiesLoaderUtils.loadProperties(resource);
	    Iterator var6 = properties.entrySet().iterator();

	    while(var6.hasNext()) {
		Entry<?, ?> entry = (Entry)var6.next();
		String factoryTypeName = ((String)entry.getKey()).trim();
		String[] var9 = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
		int var10 = var9.length;

		for(int var11 = 0; var11 < var10; ++var11) {
		    String factoryImplementationName = var9[var11];
		    result.add(factoryTypeName, factoryImplementationName.trim());
		}
	    }
	}

	cache.put(classLoader, result);
	return result;
    } catch (IOException var13) {
	throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var13);
    }
}
}
```
���ϴ��� ���ش��� �� ��ȥ�����������ļ�FACTORIES_RESOURCE_LOCATION��������ֻҪ֪����������ļ��Ǹ���ģ�
```
public final class SpringFactoriesLoader {
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
    private static final Log logger = LogFactory.getLog(SpringFactoriesLoader.class);
    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap();
```
spring.factories��springboot �Ľ�����չ���ơ����ֻ���ʵ������ģ����java��SPI��չ��ʵ�ֵġ�
��ʲô��JAVA SPI�أ�
����JAVA��SPI��ҿ��Կ����ҵĲ��͡���Ƚ���dubboԴ�롷��

�������META-INF/spring.factories�ļ������������Լ���ʵ�������ƣ�Ȼ��spring���ȡspringfactories�ļ������ݣ���ʵ����IOC������
����һ������һ�� spring.factories �ļ�����Щ������

�� spring-boot-autoconfigure-2.2.5.RELEASE.jar ��META-INF/spring.factories
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
```
�������յ�Ŀ�ľ��ǰ�spring .factories����Ū��class���ص�spring ioc�����С�

### ��������ʵս���Լ����ֱ���ʵ�ֵ�spring.factories�ļ�
ֻҪ��src/main/resource/Ŀ¼�µ�META-INF����spring.factories�ļ�����

#### ����1���½�һ�� Bean ��������
```
public class User {
    public String info(){
        return " student";
    }
}

@Configuration
public class MyConfig {

    @Bean
    public User user(){
        return new User();
    }

}
```

#### ����2���½�spring.factories
��src/main/resource Ŀ¼�µ�META-INF����spring.factories�ļ�����
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.boot.springfactories.config.MyConfig
```

#### ����3��������
```
package cn.boot.springfactoriescore.springfactoriescore;

import com.boot.springfactories.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringfactoriesCoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringfactoriesCoreApplication.class, args);
        User user = run.getBean(User.class);
        System.out.println(user.info());
    }

}
```
�����
```
 student
```

### �ģ��ܽ�@SpringBootApplication����ԭ��
��������ע���ֻ����һ���£���Beanע�ᵽSpringIoc������
ͨ��4�ַ�ʽ��ʵ�֣�
1��@SpringBootConfiguration ͨ��@Configuration ��@Bean��ϣ�ע���SpringIoc������
2��@ComponentsScan ͨ����Χ�ķ�ʽ��ɨ���ض�ע���࣬����ע�ᵽSpring Ioc ����
3��@import ͨ������ķ�ʽ��ָ����classע�뵽SpringIOC��������
4��@EnableAutoConfiguration ͨ��spring.factories�����ã���ʵ��Bean��ע�ᡣ

### �壬�κ���ϰ
�ο�����Ŀ�����������ӹ��̣�һ�����̷�һ��Student��һ�����̲���sprig.factories�ļ�������
��һ��ģ������Ķ��󡣲����ÿ��Կ��ҵ���Ŀ��ʵ�ڲ��о���ϵ�ҵ�QQ2949852842