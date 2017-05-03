package app.com.warattil.utils;


import java.util.List;

import app.com.warattil.model.SurahBean;

public interface IResponseListener {

    void success(List<SurahBean> success);

}
