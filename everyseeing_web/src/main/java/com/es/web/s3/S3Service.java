package com.es.web.s3;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Configuration 
public class S3Service {
	
	@Value("${cloud.aws.credentials.access-key}")
	private String ACCESS_KEY;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String SECRET_KEY;
	
	@Value("${cloud.aws.region.static}")
	private String REGION;

	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;
	
	private AmazonS3 s3Client;
    
	@Bean
	public AmazonS3 setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        return s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(REGION)
                .build();
    }

	
	/**
	 * 파일 업로드
	 * @param mFile
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(MultipartFile mFile, String filePath) throws Exception {
		InputStream inputStream;
		inputStream = mFile.getInputStream();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(mFile.getSize());
		metadata.setContentType(mFile.getContentType());
		try {
			s3Client.putObject(BUCKET, filePath, inputStream, metadata);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		
		return s3Client.getUrl(BUCKET, filePath).toString();
	}

	/**
	 * 파일 삭제
	 * @param filePath
	 * @throws Exception
	 */
	public void deleteFile(String filePath) throws Exception {
		s3Client.deleteObject(BUCKET, filePath);
	}
}
