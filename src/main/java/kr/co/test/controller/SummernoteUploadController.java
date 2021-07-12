package kr.co.test.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.test.common.util.RequestUtil;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2021. 7. 12. kdk	최초작성
 * </pre>
 * 
 * <pre>
 * - 그냥 확인용이므로 서비스 적용 시, 추가 설정 필요
 * - 서비스 레이어 분리 / 유효성 검사 / 파일 사이즈 체크 등등
 * </pre>
 *
 * @author kdk
 */
@RestController
@RequestMapping("/summernote")
public class SummernoteUploadController {

	// WebMvcConfig addResourceHandlers 참고
	private final String resourcePath = "/upload/";
	
	private final String editorUploadPath = "upload";
	private final String editorImgPath = "summernote";
	
	@PostMapping("/img_upload")
	public Map<String, Object> uploadFile(MultipartFile upload, HttpServletRequest request) throws IllegalStateException, IOException {
		
		//System.out.println( upload.getOriginalFilename() );
		
		String fileUrl = "";
		
		String webAppRootPath = "D:/";
		String uploadPath = webAppRootPath + editorUploadPath + "/" + editorImgPath;
		
		File dir = new File(uploadPath);
		if ( !dir.exists() ) dir.mkdirs();
		
		String fileName = upload.getOriginalFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".")+1);
		
		String saveFileName = UUID.randomUUID().toString() + "." + fileExt;
		File saveFile = new File(uploadPath + "/" + saveFileName);
		
		upload.transferTo(saveFile);
		
		String serverDomain = RequestUtil.getRequestDomain(request);
		fileUrl = serverDomain + resourcePath + editorImgPath + "/" + saveFileName;
		
		Map<String, Object> map = new HashMap<>();
		map.put("url", fileUrl);
		
		return map;
	}
	
}
