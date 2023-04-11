package com.news.main.bpp;

import java.lang.reflect.Field;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.news.main.bpp.annotations.VKRequest;
import com.news.main.enums.Topic;
import com.news.main.props.RequestProps;

import lombok.SneakyThrows;

@Component
public class VKRequestBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			VKRequest annotation = field.getAnnotation(VKRequest.class);
			setBeanFieldRequestProps(bean, field, annotation);
		}
		return bean;
	}

	@SneakyThrows
	private void setBeanFieldRequestProps(Object bean, Field field, VKRequest annotation) {
		if (annotation != null) {
			RequestProps requestProps = setRequestPropsFieldByAnnotation(annotation);
			field.setAccessible(true);
			field.set(bean, requestProps);
		}
	}

	private RequestProps setRequestPropsFieldByAnnotation(VKRequest annotation) {
		String urlRequest = annotation.getURLRequest();
		String source = annotation.getSource();
		Topic topic = annotation.getTopic();

		RequestProps requestPropsSetter = new RequestProps();

		requestPropsSetter.setSource(source);
		requestPropsSetter.setTopic(topic);
		requestPropsSetter.setURLRequest(urlRequest);

		return requestPropsSetter;

	}
}
