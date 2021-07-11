package common;

import board.model.vo.PageInfo;

public class Pagination {
// 페이징 처리 계산법을 담은 클래스
	public static PageInfo getPageInfo(int currentPage, int listCount) {
		// int currentPage, int listCount => 외부에서 값을 건네줌
		
		int pageLimit = 10;
		int maxPage;
		int startPage;
		int endPage;
		int boardLimit = 5;
		
		maxPage = (int)Math.ceil((double)listCount/boardLimit);
		
		// < 1	2	3	4	5	6	7	8	9	10	>
		// < 11	12	13	14	15	16	17	18	19	20	>
		// < 21	22	23	24	25	26	27	28	29	30	>
		//	    ㄴ 1, 11, 21, 31, ... => 10n + 1 (n >= 0)
		// 1 ~ 10 => n = 0		(10 * 0 + 1 = 1)
		// 11 ~ 20 => n = 1		(10 * 1 + 1 = 11)
		// 21 ~ 30 => n = 2		(10 * 2 + 1 = 21)
		//
		// currentPage = 현재 내가 있는 페이지 쪽수
		//
		// n = (currentPage - 1) / pageLimit 
		// ex. currentPage = 5 / pageLimit = 10 ==> n = 0
		// ex. currentPage = 17 / pageLimit = 10 ==> n = 1
		// ex. currentPage = 30 / pageLimit = 10 ==> n = 3 ??? 
		// ==> currentPage - 1을 함으로써 규칙 만족
		
		
		
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
		
		
		return pi;
	}
}
