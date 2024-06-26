package kr.or.iei.reservation.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDetail {
	private int reservationDetailNo;
	private int reservationNo;
	private int doctorNo;
	private int subjectNo;
	private String symptom;
	private String memberName;
	private int childNo;
	private int reservationStatus;
	private int reservationType;
	private String reservationTime;
	private String filename;
	private String filepath;
	private String regDate;
	private String regDay;
	private String regTime;
	private String doctorName;
	private String subjectName;
	private List<ReservationFile> fileList;
	private int reportCount;
}
