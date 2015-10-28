package epsi.fx.com.simplecalendarproject;


import java.util.List;

import epsi.fx.com.simplecalendarproject.beans.Event;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by fx on 28/10/2015.
 */
public interface WebService {

    @GET("/meetings")
    Call<List<Event>> listEvents();
}
