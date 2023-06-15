package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.bdd.BDHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    double sueldoFijo=0.00;
    double subsidio=0.00;
    double descuento=0.00;
    double horasExtras=0.00;
    EditText et_subsidio,et_sueldo,et_atrasos,et_funcionario, et_cargo, et_departamento, et_hijos,et_estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //UNIR ELEMENT0S DE BACK CON FRONT
        et_funcionario=findViewById(R.id.txtFuncionario);
        et_cargo=findViewById(R.id.txtCargo);
        et_departamento=findViewById(R.id.txtDepartamento);
        et_hijos=findViewById(R.id.txtHijos);
        et_estado=findViewById(R.id.txtEstado);
        et_atrasos=findViewById(R.id.txtAtrasos);
        et_sueldo=findViewById(R.id.txtSueldo);
        et_subsidio=findViewById(R.id.txtSubsidio);
    }
    public double determinarSueldo(String cargo){
        double sueldo=0.00;
        //String cargo=et_cargo.getText().toString();
        if (cargo.equals("Administrativo")==true) {
            sueldo=880.00;
        }else if(cargo.equals("Docente")==true){

            sueldo= 1000.00;
        }
        return sueldo;
    }

    public double subsidio(int numero){
        double sub=0.00;

        if(numero>0){
            sub=numero*50;
        }else{
            sub=0;
        }
        return sub;
    }

public double descuentoAtrasos (String item,double sueldo){
        double des=0.00;
        if(item.equals("Si")==true) {
            des = sueldo * 0.08;
        }else{
            des=0;

        }
         return des;
}
public double HorasExtras(int numHoras){
        double pagohoras=0.00;
        if (numHoras>0) {
            pagohoras = numHoras * 12;
        }else{
            pagohoras=0.00;
        }
    return 0.0;
    }
public double sueldoRecibir(double sueldoFijo,double subsidio,double descuento,double horasExtras){
        return sueldoFijo+sueldoFijo;
}

    public void registrar(View view){
        BDHelper admin=new BDHelper(this,"registro.db",null,1);
        SQLiteDatabase bd=admin.getWritableDatabase();
        String funcionario=et_funcionario.getText().toString();
        String cargo=et_cargo.getText().toString();
        String departamento=et_departamento.getText().toString();
        String hijos=et_hijos.getText().toString();
        String estado=et_estado.getText().toString();
        String atrasos=et_atrasos.getText().toString();

        if(!funcionario.isEmpty()&& !atrasos.isEmpty() && !cargo.isEmpty() && !departamento.isEmpty() && !hijos.isEmpty()&& !estado.isEmpty()){
            ContentValues registro=new ContentValues();
            registro.put("usu_funcionario",funcionario);
            registro.put("usu_cargo",cargo);
            registro.put("usu_departamento",departamento);
            registro.put("usu_hijos",hijos);
            registro.put("usu_estado",estado);
            registro.put("usu_atrasos",atrasos);
            bd.insert("tblUsuarios",null,registro);
            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
            et_funcionario.setText("");
            et_cargo.setText("");
            et_departamento.setText("");
            et_hijos.setText("");
            et_estado.setText("");
sueldoFijo=this.determinarSueldo(cargo);
et_sueldo.setText(sueldoFijo+"");

et_subsidio.setText(subsidio+"");
            //int numAtrasos=Integer.parseInt(atrasos);
            et_sueldo.setText(this.determinarSueldo(cargo)+"");
            int numHijos=Integer.parseInt(hijos);
            et_subsidio.setText(this.subsidio(numHijos)+"");

            bd.close();
        }else{
            Toast.makeText(this,"FAVOR INGRESAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }

    }
    BDHelper admin;
    SQLiteDatabase db;

    public void consulta(View v) {

        admin = new BDHelper(this, "funcionario", null, 1);

        db = admin.getWritableDatabase();
        String funcionario = et_funcionario.getText().toString();

        fila = db.rawQuery("select * from  tblUsuarios where funcionario=" + funcionario, null);

        if (fila.moveToFirst()) {
            et_funcionario.setText(fila.getString(0));
            et_cargo.setText(fila.getString(1));
            et_departamento.setText(fila.getString(2));
            et_hijos.setText(fila.getString(3));
            et_estado.setText(fila.getString(4));
            et_atrasos.setText(fila.getString(5));
        }
        else
            /*si no existe entonces lanzara un toast*/
            Toast.makeText(this, "no existe un registro con dicho codigo",
                    Toast.LENGTH_SHORT).show();
        /*cerramos la base de datos*/
        db.close();
    }
    //metodo eliminar
    public void baja(View v) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        admin = new BDHelper(this, "instituto", null, 1);
        /*abrimos la base de datos pora escritura*/
        db = admin.getWritableDatabase();
        /*creamos una variables string y lo
        inicializamos con los datos ingresados en edittext*/
        String codigo = et1.getText().toString();
        /*variable int pasamos el metodo delete de la tabla registro el codigo que edittext*/
        int cant = db.delete("registro", "codigo=" + codigo, null);
        /*cerramos la DB*/
        db.close();
        /*listamos los datos de la base de datos*/
        ArrayList array_list = admin.getAllRegistros();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1, array_list);
        /*pasamos los datos a la listview para lo muestre*/
        listaView.setAdapter(arrayAdapter);
        /*limpiamos las cajas de texto*/
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        et6.setText("");
        /*si cante es igual que unos entonces*/
        if (cant == 1)
            /*lanzamos un toast que notifique se elimino*/
            Toast.makeText(this, "se borró el registro con dicho documento",
                    Toast.LENGTH_SHORT).show();
        else
            /*de lo contrario lanzara que esos datos no existen*/
            Toast.makeText(this, "no existe un registro con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }
    //metodo modificar
    public void modificacion(View v) {
        /*instanciamos la variables de dbhelper y
        le pasamos el contexto , nombre de base de datos*/
        admin = new BDHelper(this, "instituto", null, 1);
        /*abrimos la base de datos pora escritura*/
        db = admin.getWritableDatabase();
        /*capturamos los datos de los edittext*/
        String codigo = et1.getText().toString();
        String curso = et2.getText().toString();
        String nota1 = et3.getText().toString();
        String nota2 = et4.getText().toString();
        String nota3 = et5.getText().toString();
        //calculamos el promedio
        int promedioope=0;
        promedioope=Integer.parseInt(et3.getText().toString());
        promedioope=promedioope+Integer.parseInt(et4.getText().toString());
        promedioope=promedioope+Integer.parseInt(et5.getText().toString());
        promedioope=promedioope/3;
        //covertir a string
        String promedio=String.valueOf(promedioope);
        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("curso", curso);
        registro.put("nota1", nota1);
        registro.put("nota2", nota2);
        registro.put("nota3", nota3);
        registro.put("promedio", promedio);
        /*lanzamos el metodo update con el query para que actualice segun el id o codigo*/
        int cant = db.update("registro", registro, "codigo=" + codigo, null);
        /*cerramos la BD*/
        db.close();
        /*listamos los datos de la base de
        datos para mostrarlo en el listview*/
        ArrayList array_list = admin.getAllRegistros();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1, array_list);
        /*mostramos los datos de la db*/
        listaView.setAdapter(arrayAdapter);
        /*limpiamos la caja de texto*/
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        et6.setText("");
        /*si cante es igual que unos entonces*/
        if (cant == 1)
            /*si se cumple lka sentencia entonces muestra el toast*/
            Toast.makeText(this,"se modificaron los datos",Toast.LENGTH_SHORT)
                    .show();
        else
            /*de lo contrario muestra el siguiente toast*/
            Toast.makeText(this,"no existe un registro con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }
}

}
