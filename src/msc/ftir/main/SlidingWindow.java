/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msc.ftir.main;

/**
 *
 * @author Pramuditha Buddhini
 */
public interface SlidingWindow {

    void cal_3point_avg();

    void cal_5point_avg();

    void cal_7point_avg();

    void cal_9point_avg();

    void updateSmoothedValue();

}
