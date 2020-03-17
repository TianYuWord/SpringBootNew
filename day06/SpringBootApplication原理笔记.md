# ����SpringBootApplication����ԭ��
### һ�����γ�Ŀ��
ѧϰSpring Boot�ĺ���ע��@SpringBootApplication������@SpringBootApplication��ԭ��
### ��������@SpringBootApplicationԴ��
��������������SpringBoot������ע��@SpringBootApplication
```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication 
```
@Target({ElementType.TYPE}) ע���Ŀ��λ�ã��ӿڣ��࣬ö��
@Retention(RetentionPolicy.RUNTIME) ע�����class�ֽ����ļ��д��ڣ�������ʱ����ͨ�������ȡ��
@Documented ��������JavaDoc ��Ĭ������£�javadoc�ǲ�����ע��ģ������������ע��ʱָ���� @Documented ����ᱻ JavaDoc ֮��Ĺ��ߴ���
 ����ע��������ϢҲ����������ɵ��ĵ��С�
@Inherited ���ã�����̳й�ϵ�У��������Ҫ�̳и����ע�⣬��ôҪ��ע����뱻@Inherited���ε�ע��
�������ϳ���ļ���ע�⣬ʣ�µļ�������springboot�ĺ���ע���ˡ�
@SpringBootApplication����һ������ע�⣬����@ComponentsScan����@SpringBootConfiguration��@EnableAutoConfiguration

### ����@SpringBootApplication��ֻ����һ����
��������ע���ֻ����һ���£���Beanע�ᵽSpringIoc����
ͨ��3�ַ�ʽ��ʵ��

1��@SpringBootConfiguration ͨ��@Configuration ��@Bean��ϣ�ע���SpringIoc������
2��@ComponentsScan ͨ����Χ�ķ�ʽ��ɨ���ض�ע���࣬����ע�ᵽSpring Ioc ����
3��@SpringBootApplication ͨ��spring.factories�����ã���ʵ��Bean��ע�ᡣ


### �κ���ϰ��
��ڿΣ�ͨ�����@SpringBootApplication��֪������һ������ע�⣬<br/>
���뽨һ��springboot����Ŀ��Ȼ��ɾ��@SpringBootApplication��<br/>
��@ComponentScan����SpringBootConfiguration��@EnableAutoConfiguration���棬Ȼ������springboot�����ܲ��������ɹ���<br/>
