package com.lyanba.crm.vo;

import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * @className: PaginationVO
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/23 09:16
 * @todo:
 */
public class PaginationVO<T> implements Serializable {
    private Integer total;
    private List<T> dataList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
