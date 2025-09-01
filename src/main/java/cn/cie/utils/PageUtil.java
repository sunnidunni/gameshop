package cn.cie.utils;

/**
 * Pagination utility
 */
public class PageUtil {

    private int num;        // total number of records
    private int pages;      // total number of pages
    private int current;    // current page number
    private int size;       // records per page
    private int startPos;  // starting position for database query

    public PageUtil(int num, int current, int size) {
        this.num = num;
        this.size = size;
        this.pages = num % size == 0 ? num / size : num / size + 1;
        if (current <= 0) {
            this.current = 1;
        } else {
            this.current = current > pages ? pages : current;
        }
        this.startPos = this.size * (this.current - 1) < 0 ? 0 : this.size * (this.current - 1);
    }

    /**
     * Default page size is 10
     * @param num
     * @param current
     */
    public PageUtil(int num, int current) {
        this.num = num;
        this.size = 10;
        this.pages = num % size == 0 ? num / size : num / size + 1;
        if (current <= 0) {
            this.current = 1;
        } else {
            this.current = current > pages ? pages : current;
        }
        this.startPos = this.size * (this.current - 1) < 0 ? 0 : this.size * (this.current - 1);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }
}
