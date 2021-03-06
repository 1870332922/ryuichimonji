package mod.ryuichimonji;

import java.util.Random;

public class Math2 {
	private static final Math2 instance = new Math2();

	private Math2() {
	}

	public static Math2 getInstance() {
		return instance;
	}

	public final Random RANDOM = new Random();
	public final double PHI = 1.618034;
	private static final float[] SIN_TABLE = new float[65536];

	public static float[] getSinTable() {
		return SIN_TABLE;
	}

	static {
		for (int i = 0; i < 65536; i++) {
			SIN_TABLE[i] = (float) Math.sin(i * Math.PI * 2.0 / 65536.0);
		}
		SIN_TABLE[0] = 0;
		SIN_TABLE[16384] = 1;
		SIN_TABLE[32768] = 0;
		SIN_TABLE[49152] = -1;
	}

	public static float sin(float deg) {
		return SIN_TABLE[(int) (65536.0 / 360.0 * deg) & 0xffff];
	}

	public static float cos(float deg) {
		return SIN_TABLE[((int) (65536.0 / 360.0 * deg) + 65536 / 4) & 0xffff];
	}

	public float toRadians(float angdeg) {
		return angdeg * (float) Math.PI / 180.0f;
	}

	public int clamp(int a, int min, int max) {

		return a < min ? min : (a > max ? max : a);
	}

	public float clamp(float a, float min, float max) {

		return a < min ? min : (a > max ? max : a);
	}

	public double clamp(double a, double min, double max) {

		return a < min ? min : (a > max ? max : a);
	}

	public float approachLinear(float a, float b, float max) {

		return a > b ? a - b < max ? b : a - max : b - a < max ? b : a + max;
	}

	public double approachLinear(double a, double b, double max) {

		return a > b ? a - b < max ? b : a - max : b - a < max ? b : a + max;
	}

	public float interpolate(float a, float b, float d) {

		return a + (b - a) * d;
	}

	public double interpolate(double a, double b, double d) {

		return a + (b - a) * d;
	}

	public double approachExp(double a, double b, double ratio) {

		return a + (b - a) * ratio;
	}

	public double approachExp(double a, double b, double ratio, double cap) {

		double d = (b - a) * ratio;

		if (Math.abs(d) > cap) {
			d = Math.signum(d) * cap;
		}
		return a + d;
	}

	public double retreatExp(double a, double b, double c, double ratio, double kick) {

		double d = (Math.abs(c - a) + kick) * ratio;

		if (d > Math.abs(b - a)) {
			return b;
		}
		return a + Math.signum(b - a) * d;
	}

	public double clip(double value, double min, double max) {

		if (value > max) {
			value = max;
		} else if (value < min) {
			value = min;
		}
		return value;
	}

	public boolean between(double a, double x, double b) {

		return a <= x && x <= b;
	}

	public int approachExpI(int a, int b, double ratio) {

		int r = (int) Math.round(approachExp(a, b, ratio));
		return r == a ? b : r;
	}

	public int retreatExpI(int a, int b, int c, double ratio, int kick) {

		int r = (int) Math.round(retreatExp(a, b, c, ratio, kick));
		return r == a ? b : r;
	}

	public int round(double d) {

		return (int) (d + 0.5D);
	}

	public int ceil(double d) {

		return (int) (d + 0.9999D);
	}


	public int floor(double d) {

		int i = (int) d;
		return d < i ? i - 1 : i;
	}


	public float minF(float a, float b) {

		return a < b ? a : b;
	}

	public float minF(int a, float b) {

		return a < b ? a : b;
	}

	public float minF(float a, int b) {

		return a < b ? a : b;
	}


	public float maxF(float a, float b) {

		return a > b ? a : b;
	}

	public float maxF(int a, float b) {

		return a > b ? a : b;
	}

	public float maxF(float a, int b) {

		return a > b ? a : b;
	}

	public double maxAbs(double a, double b) {

		if (a < 0.0D) {
			a = -a;
		}
		if (b < 0.0D) {
			b = -b;
		}
		return a > b ? a : b;
	}

	public int setBit(int mask, int bit, boolean value) {

		mask |= (value ? 1 : 0) << bit;
		return mask;
	}

	public boolean isBitSet(int mask, int bit) {

		return (mask & 1 << bit) != 0;
	}

}
