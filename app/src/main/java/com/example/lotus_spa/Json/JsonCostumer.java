package com.example.lotus_spa.Json;

import com.example.lotus_spa.Class.Customer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonCostumer {

    public static List<Customer> ListCustomer(String customer){

        try{
            List<Customer> custList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(customer);
            JSONObject jsonArray = jsonObject.getJSONObject("response");
            JSONArray customerArray = jsonArray.getJSONArray("customer");

            for (int i = 0; i<jsonArray.length();i++){
                jsonObject = customerArray.getJSONObject(i);

                Customer cust = new Customer();

                cust.setCustcode(jsonObject.getInt("custcode"));
                cust.setCustname(jsonObject.getString("custname"));
                cust.setCustsex(jsonObject.getString("custsex"));
                cust.setCustcpf(jsonObject.getString("custcpf"));
                cust.setCustbirthdate(jsonObject.getString("custbirthdate"));
                cust.setCusttelephone(jsonObject.getString("custtelephone"));
                cust.setCustemail(jsonObject.getString("custemail"));
                cust.setCustpassword(jsonObject.getString("custpassword"));

                custList.add(cust);
            }
            return  custList;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Customer UniqueCustumer(String customer){

        try{
            JSONObject jsonObject = new JSONObject(customer);
            JSONObject jsonArray = jsonObject.getJSONObject("response");
            JSONArray customerArray = jsonArray.getJSONArray("customer");

                jsonObject = customerArray.getJSONObject(0);

                Customer cust = new Customer();

                cust.setCustcode(jsonObject.getInt("custcode"));
                cust.setCustname(jsonObject.getString("custname"));
                cust.setCustsex(jsonObject.getString("custsex"));
                cust.setCustcpf(jsonObject.getString("custcpf"));
                cust.setCustbirthdate(jsonObject.getString("custbirthdate"));
                cust.setCusttelephone(jsonObject.getString("custtelephone"));
                cust.setCustemail(jsonObject.getString("custemail"));
                cust.setCustpassword(jsonObject.getString("custpassword"));

            return  cust;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static Boolean CheckCostumer(String customer){

        try{
            JSONObject jsonObject = new JSONObject(customer);
            JSONObject jsonArray = jsonObject.getJSONObject("response");

            if(jsonArray.getInt("quantidade") <= 0 || jsonArray.getInt("quantidade") >= 2){
                return  false;
            }
            else{
                return  true;
            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
