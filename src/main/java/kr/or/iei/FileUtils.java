package kr.or.iei;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

//Notice든, Board든 공통적으로 사용하기 위해 공통패키지 내 생성
@Component
public class FileUtils {
	//저장경로, 파일객체를 매개변수로 받아서 
	//해당 저장경로에 파일명이 중복되지 않도록 업로드하고 업로드한 파일명을 리턴하려는 함수
	public String upload(String savepath, MultipartFile file) { //Multipartfile
		String filename = file.getOriginalFilename(); 		//원폰파일명 추출 => text.txt
		//test.txt			->		test			.txt
		String onlyFilename = filename.substring(0, filename.lastIndexOf(".")); //0번~'.이하'
		String extention = filename.substring(filename.lastIndexOf(".")); // 여기까지가 .txt
		
		//실제 업로드한 파일명
		String filepath = null;
		//중복 파일명이 있으면 1씩 증가시피면서 뒤에 붙일 숫자
		int count = 0;
		while(true) {
			if(count == 0) {
				//첫번째의 경우는 숫자를 붙이지 않고 바로 검증
				filepath = onlyFilename+extention;
			}else {
				//파일명에 숫자를 붙여서 생성
				filepath = onlyFilename+"_"+count+extention;//text_1.txt
			}
			//위에 if로 만든 파일명이 사용중인지 체크
			File checkFile = new File(savepath+filepath); //안겹치면 while문 나옴
			if(!checkFile.exists()) {
				break;
			}
			count++;
		}
		//파일명 중복체크 끝 -> 내가 업로드할 파일명 결정 -> 파일 업로드 진행
		try {
			file.transferTo(new File(savepath+filepath));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath; //실제 파일명 -> 이걸 DB에 넣어두어야 함
		//try문부터 작성
	}

	public void downloadFile(String savepath, String filename, String filepath, HttpServletResponse response) {
		String downFile = savepath+filepath;
		try {
			//파일을 JAVA로 읽어오기 위한 주 스트림 생성
			FileInputStream fis = new FileInputStream(downFile);
			//속도 개선을 위한 보조 스트림 생성
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			//읽어온 파일을 사용자에게 내보낼 주 스트림 생성
			ServletOutputStream sos = response.getOutputStream();
			//속도 개선을 위한 보조스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(sos);
			
			
			//다운로드할 파일이름(사용자가 받았을 때 파일이름) 처리
			String resFilename = new String(filename.getBytes("UTF-8"),"ISO-8859-1");
			
			//파일 다운로드를 위한 HTTP 헤더 설정
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="+resFilename);
			
			//파일 전송
			while(true) {
				int read = bis.read();
				if(read != -1) {
					bos.write(read);
				}else {
					break;
				}
			}
			bos.close();
			bis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteFile(String savepath, String filepath) {
		File delFile = new File(savepath+filepath);
		//물리적인 삭제를 했을 경우 : exist로 체크한다음에 delete를 날리면 됨.
		delFile.delete();
		
		
	}
	
	public String hyokyungUpLoad(String savepath, MultipartFile file) {
	    // 파일이 없는 경우에는 파일 업로드 과정을 거치지 않고 빈 파일명을 반환합니다.
	    if (file == null || file.isEmpty()) {
	        return "";
	    }

	    String filename = file.getOriginalFilename();
	    String onlyFilename = filename.substring(0, filename.lastIndexOf("."));
	    String extention = filename.substring(filename.lastIndexOf("."));

	    String filepath = null;
	    int count = 0;
	    while(true) {
	        if(count == 0) {
	            filepath = onlyFilename + extention;
	        } else {
	            filepath = onlyFilename + "_" + count + extention;
	        }
	        File checkFile = new File(savepath + filepath);
	        if(!checkFile.exists()) {
	            break;
	        }
	        count++;
	    }

	    try {
	        file.transferTo(new File(savepath + filepath));
	    } catch (IllegalStateException | IOException e) {
	        e.printStackTrace();
	        // 파일 업로드 중 오류가 발생한 경우에는 빈 문자열을 반환합니다.
	        return "";
	    }
	    return filepath;
	}

}
