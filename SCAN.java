import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class implements the SCAN disk scheduling algorithm.
 *
 * @author ...
 */
public class SCAN implements IDiskAlgorithm {

    @Override
    public int calculateDistance(List<DiskRequest> requests, int headPosition) {
        int currentPosition = headPosition;
        int totalHeadMovement = 0;

        // Sort requests by track position
        List<DiskRequest> sortedList = sortArray(requests, headPosition);

        // Split requests into higher and lower tracks
        List<DiskRequest> higherTracks = new ArrayList<>();
        List<DiskRequest> lowerTracks = new ArrayList<>();

        for (DiskRequest request : sortedList) {
            if (request.getTrack() >= headPosition) {
                higherTracks.add(request);
            } else {
                lowerTracks.add(request);
            }
        }

        // Service requests in the upward direction
        for (DiskRequest request : higherTracks) {
            totalHeadMovement += Math.abs(currentPosition - request.getTrack());
            currentPosition = request.getTrack();
        }

        // Move to the highest track (4999) if not already there
        if (currentPosition <= 4999) {
            totalHeadMovement += Math.abs(4999 - currentPosition);
            currentPosition = 4999;
        }

        // Service requests but reversed from 4999
        for (int i = lowerTracks.size() - 1; i >= 0; i--) {
            DiskRequest request = lowerTracks.get(i);
            totalHeadMovement += Math.abs(currentPosition - request.getTrack());
            currentPosition = request.getTrack();
        }

        return totalHeadMovement;
    }

    private List<DiskRequest> sortArray(List<DiskRequest> requests, int headPosition) {
        List<DiskRequest> sortedArray = new ArrayList<>(requests);
        sortedArray.sort(Comparator.comparingInt(DiskRequest::getTrack));
        return sortedArray;
    }
}