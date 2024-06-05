package Interfaces;

import model.BugRequest;
import model.Report;

public interface IObserver {

    public void bugChanged(BugRequest bugRequest);

    public void reportChanged(Report report);


}
