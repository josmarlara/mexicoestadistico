package mx.com.tutosoftware.mexicoestadistico.model;

import java.util.ArrayList;
import java.util.List;

public class Estados {

    private List<Datos> datos = new ArrayList<>();

    public List<Datos> getDatos() {
        return datos;
    }

    public void setDatos(List<Datos> datos) {
        this.datos = datos;
    }

}
