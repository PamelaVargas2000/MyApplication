package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.bdd.BDHelper;

public class MainActivity extends AppCompatActivity {
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
        et_atrasos=findViewById(R.id.txtAtraso);
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
public void  determinarAtraso (int sueldo){
    double atr=0.00;

    if(sueldo>0){
        atr=(sueldo*8)/100;
    }else{
        atr=0;
    }
    return atr;
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
            et_atrasos.setText(this.determinarAtraso(atrasos)+"");
            int numAtrasos=Integer.parseInt(atrasos);
            et_sueldo.setText(this.determinarSueldo(cargo)+"");
            int numHijos=Integer.parseInt(hijos);
            et_subsidio.setText(this.subsidio(numHijos)+"");

            bd.close();
        }else{
            Toast.makeText(this,"FAVOR INGRESAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }

    }

}
