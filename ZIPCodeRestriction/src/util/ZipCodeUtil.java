package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import exception.ZIPCodeException;

public class ZipCodeUtil {

	private Pattern pattern = Pattern.compile("^[0-9][0-9][0-9][0-9][0-9]"); // to validate the 5 digits number
	
	private InputStream inputFileStream = null;
	
	private Map<Integer, Integer> restrictedZipCodeMap = null;
	
	public ZipCodeUtil(InputStream inputFileStream){
		this.inputFileStream = inputFileStream;
	}
	
	/**
	 * This method will return the restricted ZIp code ranges in string format
	 * @return ZIP code ranges in the specific format like [12345,23456] [34567,45678]
	 * @throws ZIPCodeException
	 */
	public String getRestrictedZipCodeRange() throws ZIPCodeException{
		String restrictedZipCodeRanges = null;
		this.getRestrictedZipCodeRangeMap();
		if(restrictedZipCodeMap.size()>0){
			StringBuilder  stringBuilder = new StringBuilder ();
			for(Map.Entry<Integer, Integer> excludedZip:restrictedZipCodeMap.entrySet()){
				stringBuilder.append("["+ ("00000" + excludedZip.getKey()).substring(excludedZip.getKey().toString().length())+","+("00000" + excludedZip.getValue()).substring(excludedZip.getValue().toString().length())+"] ");
			}
			restrictedZipCodeRanges = stringBuilder.toString().trim();
		}
		return restrictedZipCodeRanges;
		
	}
	
	/**
	 * This method will return the restricted ZIP code ranges as HashMap object
	 * @return ZIP code range as hashmap objects
	 * @throws ZIPCodeException
	 */
	public Map<Integer, Integer> getRestrictedZipCodeRangeMap() throws ZIPCodeException{
		String line;
		BufferedReader br = null;
		String[] tempStr = null;
		int lowerBound = 0, upperBound = 0;
		restrictedZipCodeMap = new TreeMap<Integer, Integer>();
		
		try {
			
			br = new BufferedReader(new InputStreamReader(inputFileStream));
			int row = 0;
			while ((line = br.readLine()) != null) {
			  row++;
			  if(line==null || line.trim().length()==0) continue; // Continue to next line if the current line has no value
			  line = line.replace(" ", "");
			  tempStr = line.replace("[", "").replace("]", "").split(","); // Split the range into lower and upper values separately
			  // If the ZIP code range format is incorrect, throw the error message
			  if(!line.startsWith("[") || !line.endsWith("]") || line.indexOf(",")<0 || tempStr.length!=2){
				  throw new IllegalArgumentException("Invaid Zip code range format in Row No: "+row+" in the input file. Sample format [10000,99999]");
			  }
			  // If lower or upper range is not in 5 digits format, throw the error message
			  if(!pattern.matcher(tempStr[0]).matches() || !pattern.matcher(tempStr[1]).matches()){
				  throw new IllegalArgumentException("Invaid Zip code range in Row No: "+row+" in the input file. Expecting 5 digits code. Sample format [10000,99999]");
			  }
			  lowerBound = Integer.parseInt(tempStr[0]);
			  upperBound = Integer.parseInt(tempStr[1]);
			  
			  //Swap lower and upper bounds if lower bound value is greater than upper bound value
			  this.swap(restrictedZipCodeMap, lowerBound, upperBound);
			  
			}
			
			if(restrictedZipCodeMap.size()==0){
				System.out.println("No data found in the input file. So all ZIP codes are valid"); // this is just for printing user defined message
			}else if(restrictedZipCodeMap.size()>1){
				this.merge(restrictedZipCodeMap);
			}
			
		} catch(IllegalArgumentException argex){
			throw new ZIPCodeException(argex.getMessage());
		}catch (Exception e) {
			if(br==null){
				throw new ZIPCodeException("Input File not found"); // if input file not exists, buffered reader would be null
			}else{
				e.printStackTrace();
				throw new ZIPCodeException("Problem occurred while finding ZIP code ranges");
			}
		} finally{
			if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new ZIPCodeException("Unable to close the input file stream");
				}
		}
		return restrictedZipCodeMap;
	}
	/**
	 * This method is used to create the input values in TreeMap object as key value pair and its sorted by key automatically
	 * Also swap the lower and upper codes if lower bound is greater than upper bound value
	 * @param zipCodeMap - TreeMap object used to keep all the ZIP code ranges 
	 * @param lowerBound - Start Range
	 * @param upperBound - End Range
	 * @return zipCodeMap after swapping the lower ad upper bound range
	 */
	private Map<Integer, Integer> swap(Map<Integer, Integer> zipCodeMap, Integer lowerBound, Integer upperBound){
		
		if(lowerBound>upperBound){
			  if(zipCodeMap.containsKey(upperBound)){
				  if(zipCodeMap.get(upperBound)<lowerBound){
					  zipCodeMap.put(upperBound, lowerBound);
				  }
			  }else{
				  zipCodeMap.put(upperBound, lowerBound);  
			  }
		  }else{
			  if(zipCodeMap.containsKey(lowerBound)){
				  if(zipCodeMap.get(lowerBound)<upperBound){
					  zipCodeMap.put(lowerBound, upperBound);
				  }
			  }else{
				  zipCodeMap.put(lowerBound, upperBound);  
			  }
		  }
		return zipCodeMap;
	}
	
	/**
	 * This method is used to merge all the restricted ZIP code ranges
	 * @param zipCodeMap - TreeMap object hold all the ZIP code range values
	 * @return zipCodeMap after merged.
	 */
	private Map<Integer, Integer> merge(Map<Integer, Integer> zipCodeMap){
		List<Integer> list = new ArrayList<Integer>(zipCodeMap.keySet());
		for(int prev=0;prev<list.size();prev++){
			if(zipCodeMap.get(list.get(prev))==null) continue;
			for(int next=prev+1;next<list.size();next++){
				if(zipCodeMap.get(list.get(next))==null) continue;
				if(zipCodeMap.get(list.get(prev))==null) continue;
				if(zipCodeMap.get(list.get(prev))==list.get(next) || zipCodeMap.get(list.get(prev))-list.get(next)==-1){
					zipCodeMap.put(list.get(prev), zipCodeMap.get(list.get(next)));
					zipCodeMap.remove(list.get(next));
				}else if(list.get(prev)<=list.get(next) && list.get(next)<=zipCodeMap.get(list.get(prev)) && list.get(prev)<=zipCodeMap.get(list.get(next)) && zipCodeMap.get(list.get(next))<=zipCodeMap.get(list.get(prev)) ){
					zipCodeMap.remove(list.get(next));
				}else if(list.get(prev)<=list.get(next) && list.get(next)<=zipCodeMap.get(list.get(prev)) && list.get(prev)<=zipCodeMap.get(list.get(next)) && zipCodeMap.get(list.get(next))>=zipCodeMap.get(list.get(prev)) ){
					zipCodeMap.put(list.get(prev), zipCodeMap.get(list.get(next)));
					zipCodeMap.remove(list.get(next));
				}
				
			}
		}
		return zipCodeMap;
	}
	
	/**
	 * this method is used to identify whether ZIP code is valid or not
	 * @param excludedZipRange - has ZIP code ranges after compressed
	 * @param inputZipCode - user input ZIP code
	 * @return false if ZIP code not valid otherwise true
	 * @throws Exception 
	 */
	public boolean isValidZip(int inputZipCode) throws ZIPCodeException{
		boolean validZip = true;
		if(restrictedZipCodeMap==null){
			this.getRestrictedZipCodeRangeMap();
		}
		if(!pattern.matcher(String.valueOf(inputZipCode)).matches()){
			throw new ZIPCodeException("Invalid ZIP code input parameter. Sample format 12345");
		}
		
		for(Map.Entry<Integer, Integer> excludedZip:restrictedZipCodeMap.entrySet()){
			if(excludedZip.getKey()<=inputZipCode && inputZipCode<=excludedZip.getValue()){
				validZip = false;
				break;
			}
		}
		return validZip;
	}
}
