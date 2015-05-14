package com.dihaitech.common.util;

/**
 * 分页工具类
 * 
 * @author qiusen
 * 
 *         2009-8-12
 */
public class Page {

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 总条数
	 */
	private long resultCount;

	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 每页显示记录数
	 */
	private int pageSize;

	/**
	 * 排序
	 */
	private String orderBy;

	private String order;

	public Page(long resultCount, int pageSize) {
		if (resultCount > 0L)
			this.resultCount = resultCount;
		if (pageSize > 0)
			this.pageSize = pageSize;
		if (resultCount > 0L && pageSize > 0)
			totalPage = (int) (((resultCount + pageSize) - 1L) / pageSize);
		page = 1;
	}

	public int getPreviousPage() {
		if (page - 1 <= 0)
			return 1;
		else
			return page - 1;
	}

	public int getNextPage() {
		if (page + 1 >= totalPage)
			return totalPage;
		else
			return page + 1;
	}

	public int getFirstItemPos() {
		int temp = (page - 1) * pageSize;
		return temp;
	}

	public long getMaxItemNum() {
		long maxItemNum = 0L;
		if (resultCount <= pageSize)
			maxItemNum = resultCount;
		else if (resultCount - (page - 1L) * pageSize >= pageSize)
			maxItemNum = pageSize;
		else
			maxItemNum = resultCount - (page - 1L) * pageSize;
		return maxItemNum;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page > totalPage && totalPage > 0)
			this.page = totalPage;
		else if (page <= 0)
			this.page = 1;
		else
			this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getResultCount() {
		return resultCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}