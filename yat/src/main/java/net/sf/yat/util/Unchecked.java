package net.sf.yat.util;

@SuppressWarnings("unchecked")
public class Unchecked {
	public static <A> A chuck(Throwable t) {
		return Unchecked.<RuntimeException, A> pervertException(t);
	}

	private static <T extends Throwable, A> A pervertException(Throwable x)
			throws T {
		throw (T) x;
	}

	public static <T extends Throwable> void chucks(Class<T> clazz) throws T {
	}

}
