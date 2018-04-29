package com.codename1.camerakit;


/**
 *  This is the public camera API
 * 
 *  @author Shai Almog
 */
public class CameraKit implements Constants {

	public CameraKit() {
	}

	/**
	 *  Will return null on platforms where Camera Kit isn't supported
	 *  @return instance of the class
	 */
	public static CameraKit create() {
	}

	/**
	 *  The component representing the camera view. Notice that start() should be invoked first
	 */
	public com.codename1.ui.PeerComponent getView() {
	}

	/**
	 *  Returns true if the camera is started, notice that start() is asynchronous so this might not be accurate
	 */
	public boolean isStarted() {
	}

	/**
	 *  Must be invoked before using the camera
	 */
	public void start() {
	}

	public void stop() {
	}

	public float getVerticalViewingAngle() {
	}

	public float getHorizontalViewingAngle() {
	}

	public int getFacing() {
	}

	public boolean isFacingFront() {
	}

	public boolean isFacingBack() {
	}

	public void setFacing(int facing) {
	}

	public void setFlash(int flash) {
	}

	public int getFlash() {
	}

	public void setFocus(int focus) {
	}

	public void setMethod(int method) {
	}

	public void setPinchToZoom(boolean zoom) {
	}

	public void setZoom(float zoom) {
	}

	public void setPermissions(int permissions) {
	}

	public void setVideoQuality(int videoQuality) {
	}

	public void setVideoBitRate(int videoBirRate) {
	}

	public void setLockVideoAspectRatio(boolean lockVideoAspectRatio) {
	}

	public void setJpegQuality(int jpegQuality) {
	}

	public void setCropOutput(boolean cropOutput) {
	}

	public int toggleFacing() {
	}

	public int toggleFlash() {
	}

	public void captureImage() {
	}

	public void captureVideo() {
	}

	public void captureVideo(String videoFile) {
	}

	public void stopVideo() {
	}

	public int getPreviewWidth() {
	}

	public int getPreviewHeight() {
	}

	public int getCaptureWidth() {
	}

	public int getCaptureHeight() {
	}

	public void addCameraListener(CameraListener listener) {
	}

	public void removeCameraListener(CameraListener listener) {
	}

	public void fireCameraErrorEvent(CameraEvent ev) {
	}

	public void fireCameraImageEvent(CameraEvent ev) {
	}

	public void fireCameraVideoEvent(CameraEvent ev) {
	}
}
