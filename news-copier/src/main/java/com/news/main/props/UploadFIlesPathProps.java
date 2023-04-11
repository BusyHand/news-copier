package com.news.main.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "upload.var")
@Data
public class UploadFIlesPathProps {

	private String uploadPath = "src/main/resources/static/uploads";
	
}

