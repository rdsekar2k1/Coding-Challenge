package main;
import util.ZipCodeUtil;


public class ZIPCodeRangeMain {

	/**
	 * Run the program with or without arguments.
	 * If no argument passed, program will process the input file and return the result
	 * If argument passed,program will process the input file and return the result. Also it validate the input and return whether give ZIP code is valid or not 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeMain.class.getResourceAsStream("/restriction.txt"));
		System.out.println(zipCodeUtil.getRestrictedZipCodeRangeMap());
		
			if(args.length>0 && args[0]!=null){
				try{
				int inputZipCode = Integer.parseInt(args[0]);
				if(!zipCodeUtil.isValidZip(inputZipCode)){
					System.out.println("Given ZIP code "+args[0]+" is not valid");
				}else{
					System.out.println("Given ZIP code "+args[0]+" is valid");
				}
				}catch(NumberFormatException nfe){
					System.out.println("Please enter ZIP code in numeric");
				}
			}
		
	}
}