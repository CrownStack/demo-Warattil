package app.com.warattil.utils;


import java.util.List;

import app.com.warattil.model.Surah;

public interface IResponseListener {

    void success(List<Surah> success);
}
