import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class will implement the C-SCAN 
 * disk scheduling algorithm.
 * 
 * @author ...
 */
public class CSCAN implements IDiskAlgorithm {

@Override
public int calculateDistance(List<DiskRequest> requests, int headPosition) {

int currentPosition = headPosition;
int totalTime = 0;

List<DiskRequest> sortedList = sortArray(requests, headPosition);
List<DiskRequest> resetArray = new ArrayList<>();
int maxCylinder = 4999;

while(sortedList.size() > 0){

for(int i = 0; i < sortedList.size(); i++){
DiskRequest newList = sortedList.get(i);
int newListTime = newList.getTimeOfArrival();
int newPositionTrack = newList.getTrack();

if (currentPosition > newPositionTrack){
int toCylinderDifference = Math.abs(maxCylinder - currentPosition);
totalTime += toCylinderDifference;
totalTime += 4999;
currentPosition = 0;
}

if (newListTime > totalTime){

resetArray.add(newList);
} else {

int difference = Math.abs(newPositionTrack - currentPosition);
totalTime += difference;
currentPosition = newPositionTrack;
}
}
if (resetArray.size() > 0) {
int toCylinderDifference = Math.abs(maxCylinder - currentPosition);
totalTime += toCylinderDifference;
totalTime += 4999;
currentPosition = 0;
}

sortedList = resetArray;
resetArray = new ArrayList<>();
}

return totalTime;
}


private List<DiskRequest> sortArray(List<DiskRequest> requests, int headPosition) {

List<DiskRequest> sortedArray = new ArrayList<>();

Comparator<DiskRequest> compareTrack = Comparator.comparing(DiskRequest::getTrack);
List<DiskRequest> sortedRequests = requests.stream().sorted(compareTrack).toList();

sortedArray.add(new DiskRequest(headPosition, 0));
sortedArray.addAll(sortedRequests);

List<DiskRequest> tempArray = new ArrayList<>();

for(int i = sortedArray.size() - 1; i >= 0; i -= 1) {
DiskRequest currentRequest = sortedArray.get(i);
if (currentRequest.getTrack() < headPosition){
sortedArray.remove(currentRequest);
tempArray.add(currentRequest);
}
}

Collections.reverse(tempArray);
sortedArray.addAll(tempArray);

return sortedArray;
}

}
