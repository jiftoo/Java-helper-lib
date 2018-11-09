package encryption;

import java.util.ArrayList;

public final class EncResult
{
	private final char[] res;
	private final long time; // Test
	public EncResult(char[] res, long time) {
		this.res = res;
		this.time = time;
	}
	public long time()
	{
		return time;
	}
	public String get()
	{
		return new String(res);
	}
	public void print()
	{
		System.out.println(res);
	}
	@Override
	public String toString() {
		return this.get();
	}
	@Override
	public int hashCode() {
		ArrayList<Integer> ints = new ArrayList<>(res.length*2);
		for(int i = 0;i < res.length;i++)
		{
			if(!Character.isDigit(res[i]))
				ints.add((Integer)(~res[i]) | (~0xff << 1));
			else
				ints.add((Integer)(res[i] * 2 >> 3) | ~31);
		}
		for(int i = 0;i < res.length;i++)
		{
			if(!Character.isDigit(res[i]))
				ints.add((Integer)((res[i] >> 2) | 0x666c));
			else
				ints.add((Integer)(res[i]*2 | 1));
		}
		ints.add(Integer.parseInt(new String(res).replaceAll("\\D+", "9").substring(0, 9)) * ~0x666c);
		
		System.out.println(ints.toString());
		return ints.toString().hashCode();
	}
}
