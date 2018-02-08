package com.chenxyz.configBean;

import java.io.Serializable;

/**
 * 配置基本类
 *
 * @author chenxyz
 * @version 1.0
 * @date 2018-02-05
 */
public class BaseConfigBean implements Serializable {

    private static final long serialVersionUID = -888550311558336285L;

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
