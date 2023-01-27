package com.human.domain;

import lombok.Data;

@Data
public class Page {
	
	// 기본값
	private static final int PAGE_NO = 1;			// 현재 번호
	private static final int ROWS_PER_PAGE = 10;	// 페이지당 행의 수	
	private static final int PAGE_COUNT = 10;		// 노출 페이지 개수
	
	private int pageNo;						// 현재 번호
	private int rowsPerPage;				// 페이지 당 행의 수
	private int pageCount;					// 노출 페이지 개수
	private int totalCount;					// 전체 데이터 개수
	
	private int startPage;					// 시작 번호
	private int endPage;					// 끝 번호
	
	private int firstPage;					// 첫 번호
	private int lastPage;					// 마지막 번호
	
	private int prev;						// 이전 번호
	private int next;						// 다음 번호
	
	private int startRowNo;					// 시작 행 번호
	private int endRowNo;					// 끝 행 번호
	
	// 생성자
	public Page() {
		this( PAGE_NO, ROWS_PER_PAGE, PAGE_COUNT, 0 );
	}
	
	public Page(int pageNo, int rowsPerPage, int pageCount, int totalCount) {
		this.pageNo = pageNo;
		this.rowsPerPage = rowsPerPage;
		this.pageCount = pageCount;
		this.totalCount = totalCount;
		paging();		// 페이징 처리
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		paging();		// 페이징 처리
	}
	
	
	// 페이징 처리 수식 메소드
	public void paging() {
		
		this.prev = pageNo - 1;
		this.next = pageNo + 1;
		
		this.firstPage = 1;
		// (전체 데이터 개수-1) / 페이지당 게시물 수 + 1
		this.lastPage = (totalCount -1) / rowsPerPage + 1;
		
		// ((현재 번호-1) / 노출 페이지 개수) * 노출 페이지 개수 + 1
		this.startPage = ( (pageNo-1) / pageCount ) * pageCount + 1;
		
		// ( ((현재 번호-1) / 노출 페이지 개수) + 1 ) * 노출 페이지 개수 + 1
		this.endPage = ( ((pageNo-1) / pageCount) + 1 ) * pageCount;
		
		// 끝 페이지가 마지막 페이지보다 클 때,
		// 실제 데이터 개수를 반영한 끝페이지로 보정
		if( this.lastPage > 1 && this.endPage > this.lastPage ) {
			this.endPage = this.lastPage;
		}
		
		this.startRowNo = ( this.pageNo - 1 ) * this.rowsPerPage + 1;
		this.endRowNo = this.pageNo * this.rowsPerPage;
		
	}





	

}
















