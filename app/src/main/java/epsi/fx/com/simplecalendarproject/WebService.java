package epsi.fx.com.simplecalendarproject;


import java.util.List;

import epsi.fx.com.simplecalendarproject.beans.Event;
import epsi.fx.com.simplecalendarproject.beans.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by fx on 28/10/2015.
 */
public interface WebService {

    @GET("/meetings")
    Call<List<Event>> listEvents();

    @POST("/users")
    Call<Void> register(@Body User u);

}
