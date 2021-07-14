package kr.co.test.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tika.Tika;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.test.common.util.RequestUtil;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 7. 14. 김대광	최초작성
 * </pre>
 *
 * <pre>
 * - 그냥 확인용이므로 서비스 적용 시, 추가 설정 필요
 * - 서비스 레이어 분리 / 파일 사이즈 체크 등등
 * </pre>
 *
 * @author 김대광
 */
@RestController
public class EditorUploadController {

	// WebMvcConfig addResourceHandlers 참고
	private final String resourcePath = "/upload/";
	
	private final String editorUploadPath = "upload";
	
	@PostMapping("/{editorType}/img_upload")
	public Map<String, Object> uploadFile(
			@PathVariable String editorType,
			MultipartFile upload, HttpServletRequest request) throws IllegalStateException, IOException {
		
		Map<String, Object> map = new HashMap<>();
		
		//System.out.println( upload.getOriginalFilename() );
		
		String mimeType = this.getFileMimeTypeTika( upload.getInputStream() );
		
		if ( !mimeType.split("/")[0].equals("image") ) {
			return map;
		}
		
		String webAppRootPath = "D:/";
		String uploadPath = webAppRootPath + editorUploadPath + "/" + editorType;
		
		File dir = new File(uploadPath);
		if ( !dir.exists() ) dir.mkdirs();
		
		String fileName = upload.getOriginalFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
		
		String saveFileName = UUID.randomUUID().toString() + "." + fileExt;
		File saveFile = new File(uploadPath + "/" + saveFileName);
		
		upload.transferTo(saveFile);
		
		String serverDomain = RequestUtil.getRequestDomain(request);
		String fileUrl = serverDomain + resourcePath + editorType + "/" + saveFileName;
		
		map.put("url", fileUrl);
		
		return map;
	}
	
	/**
	 * MIME 타입 가져오기
	 * @param is
	 * @return
	 */
	private String getFileMimeTypeTika(InputStream is) {
		String mimeType = "";
		Tika tika = new Tika();

		try {
			/*
			 * File 로도 가능하나 확장자 체크하는거하고 차이가 없다.
			 * InputStream 으로 체크해야 원 파일의 MIME 타입을 가지고 온다. 
			 */
			mimeType = tika.detect(is);

		} catch (IOException e) {
			System.err.println(e);
		}

		return mimeType;
	}
	
}
