package mx.com.tutosoftware.mexicoestadistico.service;

import mx.com.tutosoftware.mexicoestadistico.model.Estados;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DatosService {

    String API_ROUTE = "mgee/";

    @GET(API_ROUTE)
    Call<Estados> getData();


}
