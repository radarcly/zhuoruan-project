package Entity;

import java.util.List;

public class Page<T> {
    private int currentPage = 1; // 当前页, 默认显示第一页
    private int pageCount = 3;   // 每页显示的行数(查询返回的行数), 默认每页显示3行
    private int totalCount;      // 总记录数
    private int totalPage;       // 总页数 = 总记录数 / 每页显示的行数  (+ 1)
    private List<T> pageData;       // 分页查询到的数据
    private List<T> AllData;

    // 返回总页数
    public int getTotalPage() {
        if (totalCount % pageCount == 0) {
            totalPage = totalCount / pageCount;
        } else {
            totalPage = totalCount / pageCount + 1;
        }
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageData() {
        return pageData;
    }
    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }


    public List<T> getAllData() {
        return AllData;
    }

    public void setAllData(List<T> allData) {
        AllData = allData;
    }
}