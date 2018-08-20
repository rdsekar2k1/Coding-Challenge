package junit;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import util.ZipCodeUtil;
import exception.ZIPCodeException;

public class ZIPCodeRangeUnitTest {

	@Rule
	public ExpectedException expEx = ExpectedException.none();

	/**
	 * Get the minimum number of restricted ZIP code ranges in string format 
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */

	@Test
	public void compareZipCodeRangeString1() throws ZIPCodeException, FileNotFoundException {

		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction1.txt"));
		Assert.assertEquals(zipCodeUtil.getRestrictedZipCodeRange(), "[94133,94133] [94200,94399]");
    
	}
	
	/**
	 * Get the minimum number of restricted ZIP code ranges in HashMap object 
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */

	@Test
	public void compareZipCodeRangeMap1() throws ZIPCodeException, FileNotFoundException {

		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction1.txt"));
		Map<Integer, Integer> expectedZipCodeRanges = new HashMap<Integer, Integer>();
		expectedZipCodeRanges.put(94133, 94133);
		expectedZipCodeRanges.put(94200, 94399);
		Assert.assertEquals(zipCodeUtil.getRestrictedZipCodeRangeMap(), expectedZipCodeRanges);
    }

	/**
	 * Get the minimum number of restricted ZIP code ranges in string format
	 * Lower and upper bound inputs would be in reverse order
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */

	@Test
	public void compareZipCodeRangeString2() throws ZIPCodeException, FileNotFoundException {

		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction2.txt"));
		Assert.assertEquals(zipCodeUtil.getRestrictedZipCodeRange(), "[93100,94134] [94200,94399]");
	}
	
	/**
	 * Get the minimum number of restricted ZIP code ranges in string format
	 * Lower and upper bound inputs would be in reverse order
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */

	@Test
	public void compareZipCodeRangeMap2() throws ZIPCodeException, FileNotFoundException {

		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction2.txt"));
		Map<Integer, Integer> expectedZipCodeRanges = new HashMap<Integer, Integer>();
		expectedZipCodeRanges.put(93100, 94134);
		expectedZipCodeRanges.put(94200, 94399);
		Assert.assertEquals(zipCodeUtil.getRestrictedZipCodeRangeMap(), expectedZipCodeRanges);
	}
	
	/**
	 * Expecting exception when the input is not in 5 digits format
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */
	@Test
	public void compareZipCodeRangeString3() throws ZIPCodeException, FileNotFoundException {
		expEx.expect(ZIPCodeException.class);
		expEx.expectMessage("Invaid Zip code range in Row No: 1 in the input file. Expecting 5 digits code. Sample format [10000,99999]");
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction3.txt"));
		zipCodeUtil.getRestrictedZipCodeRange();
	}
	
	/**
	 * Expecting exception when the input is not in expected format
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */
	@Test
	public void compareZipCodeRangeString4() throws ZIPCodeException, FileNotFoundException {
		expEx.expect(ZIPCodeException.class);
		expEx.expectMessage("Invaid Zip code range format in Row No: 2 in the input file. Sample format [10000,99999]");
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction4.txt"));
		zipCodeUtil.getRestrictedZipCodeRange();
	}
	
	/**
	 * Expecting NULL if no data found in the input file. No ZIP code restriction
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */
	@Test
	public void compareZipCodeRangeString5() throws ZIPCodeException, FileNotFoundException {
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction5.txt"));
		Assert.assertNull(zipCodeUtil.getRestrictedZipCodeRange());
	}
	
	/**
	 * Check if the given ZIP code is valid or not. It return true if the ZIP code not falls within the range
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */
	@Test
	public void checkValidZipCode() throws ZIPCodeException, FileNotFoundException {
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction1.txt"));
		Assert.assertTrue(zipCodeUtil.isValidZip(94134));
	}
	
	/**
	 * Check if the given ZIP code is valid or not. It return false for the ZIP code falls within the range
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */
	@Test
	public void checkInValidZipCode() throws ZIPCodeException, FileNotFoundException {
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction1.txt"));
		Assert.assertFalse(zipCodeUtil.isValidZip(94355));
	}
	
	/**
	 * Check if the given ZIP code is valid or not. It return false for the ZIP code falls within the range
	 * @throws ZIPCodeException
	 * @throws FileNotFoundException
	 */
	@Test
	public void checkInValidZipCode2() throws ZIPCodeException, FileNotFoundException {
		expEx.expect(ZIPCodeException.class);
		expEx.expectMessage("Invalid ZIP code input parameter. Sample format 12345");
		ZipCodeUtil zipCodeUtil = new ZipCodeUtil(ZIPCodeRangeUnitTest.class.getResourceAsStream("restriction1.txt"));
		zipCodeUtil.isValidZip(94389855);
	}

}
