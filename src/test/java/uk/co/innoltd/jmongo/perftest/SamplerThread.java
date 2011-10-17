package uk.co.innoltd.jmongo.perftest;

abstract class SamplerThread extends Thread {
	private int count;
	private long duration;

	public SamplerThread(int count) {
		this.count = count;
	}

	@Override
	public void run() {
		duration = System.currentTimeMillis();
		try {
			for (int i = count; i > 0; i--) {
				doIt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		duration = System.currentTimeMillis() - duration;
	}

	public abstract void doIt() throws Exception;

	public long getDuration() {
		return duration;
	}

}
