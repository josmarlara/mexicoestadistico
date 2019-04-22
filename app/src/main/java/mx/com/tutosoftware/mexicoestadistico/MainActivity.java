package mx.com.tutosoftware.mexicoestadistico;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import mx.com.tutosoftware.mexicoestadistico.model.Datos;
import mx.com.tutosoftware.mexicoestadistico.model.Estados;
import mx.com.tutosoftware.mexicoestadistico.service.DatosService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> estados = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    List<Datos> datosInegi= new ArrayList<Datos>();
    String poblacionFemenina;
    String poblacionMasculina;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,estados);
        spinner = (Spinner) findViewById(R.id.spinner1);
        pieChart = findViewById(R.id.piechart);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                poblacionFemenina =  datosInegi.get(position).getPob_fem();
                poblacionMasculina=  datosInegi.get(position).getPob_mas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getDatosEstado();
    }
    private void getDatosEstado(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gaia.inegi.org.mx/wscatgeo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DatosService datosService = retrofit.create(DatosService.class);
        Call<Estados> call = datosService.getData() ;



        call.enqueue(new Callback<Estados>() {
            @Override
            public void onResponse(Call<Estados> call, Response<Estados> response) {

                Estados estadosI = response.body();




                datosInegi= estadosI.getDatos();

                for(Datos datosE : datosInegi){
                    estados.add(datosE.getNom_agee());
                }


                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Estados> call, Throwable t) {

            }
        });

    }
    public void graficar(View view){

        float numMujeres = Float.parseFloat(poblacionFemenina);
        float numHombres = Float.parseFloat(poblacionMasculina);

        ArrayList<PieEntry> habitantes = new ArrayList<PieEntry>();
        habitantes.add(new PieEntry(numMujeres,"Mujeres"));
        habitantes.add(new PieEntry(numHombres,"Hombres"));
        PieDataSet dataSet = new PieDataSet(habitantes,"Número de habitantes");
        dataSet.setValueTextSize(12);
        ArrayList<String> tipoHabitante = new ArrayList<String>();

        PieData data = new PieData(dataSet);


        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.animateXY(5000,5000);

    }
}
