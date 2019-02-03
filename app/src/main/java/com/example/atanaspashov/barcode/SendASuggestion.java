package com.example.atanaspashov.barcode;


import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SendASuggestion extends AsyncTask<String, Void, Void> {
    private ServerSocket sendSocket;
    private Socket recvSocket;
    private String barcode, name, desc, materials;
    public SendASuggestion() {

    }
    @Override
    protected Void doInBackground(String... Description) { // TODO pass in a struct?
        try {
            Socket socket = new Socket("192.168.1.135", 12000);
            OutputStream out = socket.getOutputStream();
            PrintWriter output = new PrintWriter(out, true);

            // TODO test
            barcode = Description[0];
            name = Description[1];
            desc = Description[2];
            materials = Description[3];
            output.println("test:\n" + barcode + "\n" + name + "\n" + desc + "\n" + materials);

            output.close();
            out.close();
            Log.w("COW", "sent");
            socket.close();
        } catch (Exception e) {
            System.out.println("Could not establish a connection");
            System.out.println(e);
            Log.w("COW", e);
            return null;
        }
        //
       return null;
    }
    @Override
    protected void onProgressUpdate(Void... v) {

    }
    @Override
    protected void onPostExecute(Void v) {

    }
}
