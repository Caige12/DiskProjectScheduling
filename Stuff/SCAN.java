import java.util.List;

/**
 * This class will implement the SCAN 
 * disk scheduling algorithm.
 * 
 * @author ...
 */
public class SCAN implements IDiskAlgorithm {

	@Override

	List<DiskRequest> requests = null;
	int headPosition = 0;
	int timeOfArrival = 0;
	int track = null;

	public int calculateDistance(List<DiskRequest> requests, int headPosition) {
		List<DiskRequest> Head = new ArrayList<>();
		List<DiskRequest> Body = new ArrayList<>();

		for(requests : requests) {
			if(requests.track < headPosition) {
				Head.add(requests);
			} else {
				Body.add(requests);
			}
		}
		int Movement = 0;
		int currentPosition = headPosition;
	}


}
