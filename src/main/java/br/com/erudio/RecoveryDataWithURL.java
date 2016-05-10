package br.com.erudio;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.erudio.beans.AddressInfo;

public class RecoveryDataWithURL {

    private static String url = "http://cep.desenvolvefacil.com.br/BuscarCep.php?cep=38400242&ret=xml";
        
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        readXMLURL(url);
    }

    private static void readXMLURL(String url) throws IOException {
        
        HttpClient client = new DefaultHttpClient();
        HttpGet method = new HttpGet(url);
        HttpResponse httpResponse = client.execute(method);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            InputStream inputStream = httpResponse.getEntity().getContent();
            parserXMLToObject(inputStream);
        }
    }
    
    private static AddressInfo parserXMLToObject(InputStream inputStream) {
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(AddressInfo.class);
        AddressInfo addressInfo = (AddressInfo) xStream.fromXML(inputStream);
        System.out.println(addressInfo.getCity() + " - " + addressInfo.getStreet());
        return addressInfo;
    }
}