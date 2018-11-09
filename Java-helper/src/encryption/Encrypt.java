package encryption;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;

public class Encrypt {
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	private static enum DTYPE
	{
		STRING, OBJ, OTHER;
	}
	
	static <TYPE> EncResult _enc_internal(final TYPE toEnc, final String algo, DTYPE t)
	{
		synchronized(Encrypt.class) {
			long st = System.nanoTime();
			
			MessageDigest mdig = null;
			try
			{
				mdig = MessageDigest.getInstance(algo);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			byte[] sorted_todigest;
			if(t == DTYPE.STRING)
				sorted_todigest = ((String)toEnc).getBytes(StandardCharsets.UTF_8);
			else if(t == DTYPE.OBJ)
				sorted_todigest = (toEnc.toString() + (toEnc.getClass().getName() + "@" + Integer.toHexString(toEnc.hashCode()))).getBytes(StandardCharsets.UTF_8);
			else
				sorted_todigest = toEnc.toString().getBytes(StandardCharsets.UTF_8);
			
			byte[] digested_bytes = mdig.digest(sorted_todigest);
					
		    char[] hexChars = new char[digested_bytes.length * 2];
		    
		    for ( int j = 0; j < digested_bytes.length; j++ ) {
		        int v = digested_bytes[j] & 0xFF;
		        hexChars[j * 2] = hexArray[v >>> 4];
		        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		    }
		    return new EncResult(hexChars, System.nanoTime() - st);
		}
	}
	
	static String bench(final String alg)
	{
		synchronized(Encrypt.class) {
			BigInteger bint = BigInteger.valueOf(0);
			long first_calc = 0;
			long tenth_calc = 0;
			ThreadLocalRandom loc_rand = ThreadLocalRandom.current();
			for(int i=0;i<9999;i++)
			{
				bint = bint.add(BigInteger.valueOf(Encrypt._enc_internal(String.valueOf(loc_rand.nextInt()), alg, DTYPE.STRING).time()));
				if(i==0) 	first_calc = bint.intValue();
				if(i==10) 	tenth_calc = Encrypt._enc_internal(String.valueOf(loc_rand.nextInt()), alg, DTYPE.STRING).time();
			}
			BigInteger bint_res = bint.divide(BigInteger.valueOf(9999));
			
			try {
				return (String.valueOf(first_calc) + "|" + String.valueOf(tenth_calc) + "|" + bint_res.longValueExact());
			} catch (ArithmeticException ae) {
				System.err.println("Your hardware friccin' weak! Average value somehow doesnt fit into long!");
				System.err.println("Here's the strimg representation: " + bint.toString());
				return "lmao_noob|" + bint.toString();
			}
		}
	}
	
	public static final class md5
	{
		/**
		 * Performs an encryption of a given String.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	String(String enc) 	{return Encrypt._enc_internal(enc, "MD5", DTYPE.STRING);}
		/**
		 * Performs an encryption of a given Integer.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	Int(Integer enc) 	{return Encrypt._enc_internal(enc, "MD5", DTYPE.OTHER);}
		/**
		 * Performs an encryption of a given Float.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public 		EncResult 	Float(Float enc) 	{return Encrypt._enc_internal(enc, "MD5", DTYPE.OTHER);}
		/**
		 * Performs an encryption of a given Double.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	Double(Double enc) 	{return Encrypt._enc_internal(enc, "MD5", DTYPE.OTHER);}
		/**
		 * Performs an encryption of a given Byte.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public 		EncResult 	Byte(Byte enc) 		{return Encrypt._enc_internal(enc, "MD5", DTYPE.OTHER);}
		/**
		 * Performs an encryption of a given Character.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public	   	EncResult 	Char(Character enc) {return Encrypt._enc_internal(enc, "MD5", DTYPE.OTHER);}
		/**
		 * Performs an encryption of a given Object.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 15745647 nanoseconds
		 * Tenth calculation: 50355 nanoseconds
		 * Average time: 10785 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * All first-time calculations (since JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	Object(Object enc)		{return Encrypt._enc_internal(enc, "MD5", DTYPE.OBJ);}
		
		/**
		 * 
		 */
		static public 		String		Bench()				{return Encrypt.bench("md5");}
}
	public static final class sha256
	{
		/**
		 * Performs an encryption of a given String.<br>
		 * Supports concurrency!<br><br>
		 * 
		 * Clear jvm 1.8 x64 test<br>
		 * fx-6300 4ghz speed (10000 hashes total):<br><br>
		 * 
		 * First calculation: 17505158 nanoseconds<br>
		 * Tenth calculation: 87022 nanoseconds<br>
		 * Average time: 12631 nanoseconds<br><br>
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * The first-time calculation (the first one after JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	String(String enc) 	{return Encrypt._enc_internal(enc, "SHA-256", DTYPE.STRING);}
		static public		EncResult 	Int(Integer enc) 	{return Encrypt._enc_internal(enc, "SHA-256", DTYPE.OTHER);}
		static public 		EncResult 	Float(Float enc) 	{return Encrypt._enc_internal(enc, "SHA-256", DTYPE.OTHER);}
		static public		EncResult 	Double(Double enc) 	{return Encrypt._enc_internal(enc, "SHA-256", DTYPE.OTHER);}
		static public 		EncResult 	Byte(Byte enc) 		{return Encrypt._enc_internal(enc, "SHA-256", DTYPE.OTHER);}
		static public	   	EncResult 	Char(Character enc) {return Encrypt._enc_internal(enc, "SHA-256", DTYPE.OTHER);}
		static public 	 	EncResult 	Object(Object enc)	{return Encrypt._enc_internal(enc, "SHA-256", DTYPE.OBJ);}
		
		static public 		String		Bench()				{return Encrypt.bench("sha-256");}
	}
	public static final class sha512
	{
		/**
		 * Performs an encryption of a given String.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 16481424 nanoseconds
		 * Tenth calculation: 78222 nanoseconds
		 * Average time: 12016 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * The first-time calculation (the first one after JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	String(String enc) 	{return Encrypt._enc_internal(enc, "SHA-512", DTYPE.STRING);}
		static public		EncResult 	Int(Integer enc) 	{return Encrypt._enc_internal(enc, "SHA-512", DTYPE.OTHER);}
		static public 		EncResult 	Float(Float enc) 	{return Encrypt._enc_internal(enc, "SHA-512", DTYPE.OTHER);}
		static public		EncResult 	Double(Double enc) 	{return Encrypt._enc_internal(enc, "SHA-512", DTYPE.OTHER);}
		static public 		EncResult 	Byte(Byte enc) 		{return Encrypt._enc_internal(enc, "SHA-512", DTYPE.OTHER);}
		static public	   	EncResult 	Char(Character enc) {return Encrypt._enc_internal(enc, "SHA-512", DTYPE.OTHER);}
		static public 	 	EncResult 	Object(Object enc)	{return Encrypt._enc_internal(enc, "SHA-512", DTYPE.OBJ);}
		
		static public 		String		Bench()				{return Encrypt.bench("sha-512");}
	}
	public static final class sha
	{
		/**
		 * Performs an encryption of a given String.
		 * Supports concurrency!
		 * 
		 * Clear jvm 1.8 x64 test
		 * fx-6300 4ghz speed (10000 hashes total):
		 * 
		 * First calculation: 14076090 nanoseconds
		 * Tenth calculation: 60622 nanoseconds
		 * Average time: 11048 nanoseconds
		 * 
		 * NOTE: The time does in fact reduce with every iteration, so after 10-50 cycles you time will be much
		 * lower and more consistent.
		 * The first-time calculation (the first one after JVM startup) take the most time,
		 * then if you proceed to hash, your time will be getting lower and lower
		 * EVEN IF YOU CALL THE FUNCTION AFTER SEVERAL MINUTES AND/OR FROM ANOTHER THREAD.
		 * 
		 * You can use the {@link #Bench() Bench} method to benchmark you hashing performance.
		 * 
		 * @param enc
		 * String to encrypt.
		 * @return
		 * EncResult - utility class, which provides helper methods such as {@link encryption.EncResult#print() print} or {@link encryption.EncResult#time() time},
		 * also toString() is overridden to return the hash string (equivalent to calling {@link encryption.EncResult#get() get}).
		 */
		static public		EncResult 	String(String enc) 	{return Encrypt._enc_internal(enc, "SHA", DTYPE.STRING);}
		static public		EncResult 	Int(Integer enc) 	{return Encrypt._enc_internal(enc, "SHA", DTYPE.OTHER);}
		static public 		EncResult 	Float(Float enc) 	{return Encrypt._enc_internal(enc, "SHA", DTYPE.OTHER);}
		static public		EncResult 	Double(Double enc) 	{return Encrypt._enc_internal(enc, "SHA", DTYPE.OTHER);}
		static public 		EncResult 	Byte(Byte enc) 		{return Encrypt._enc_internal(enc, "SHA", DTYPE.OTHER);}
		static public	   	EncResult 	Char(Character enc) {return Encrypt._enc_internal(enc, "SHA", DTYPE.OTHER);}
		static public 	 	EncResult 	Object(Object enc)	{return Encrypt._enc_internal(enc, "SHA", DTYPE.OBJ);}
		
		static public 		String		Bench()				{return Encrypt.bench("sha");}
	}
}

