import javazoom.jl.decoder.JavaLayerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CheckAlive {


	public static void CheckIfIsAlive() throws IOException, JavaLayerException, InterruptedException {

		Process allProcess = Runtime.getRuntime().exec("tasklist");

		BufferedReader processes = new BufferedReader(new InputStreamReader(allProcess.getInputStream()));


		List<String> converted = new ArrayList<>();

		String oneLine;


		while((oneLine = processes.readLine()) != null){

			if(oneLine.contains("null")){
				processes.close();
			}
			  converted.add(oneLine);


		}



		for(int b = 2; b < converted.size(); b++){


			String asd = converted.get(b).substring(0,30);

			boolean isTrue = true;

		while(isTrue) if (asd.contains("java.exe")) {
			System.out.println("It's all good");

		} else {

          for(int run = 0; run <= 3;run++){
			  MainRunner.main(null);
		  }

			isTrue = false;
		}


		}



	}

}
