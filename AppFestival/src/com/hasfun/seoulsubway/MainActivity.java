package com.hasfun.seoulsubway;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.hasfun.seoulsubway.common.Constants;

public class MainActivity extends Activity implements
		MapView.OpenAPIKeyAuthenticationResultListener,
		MapView.MapViewEventListener, MapView.CurrentLocationEventListener,
		MapView.POIItemEventListener,
		MapReverseGeoCoder.ReverseGeoCodingResultListener {

	private final Logger log = Logger.getLogger(this.getClass());
	ArrayAdapter<String> arrayAdapter;

	private static final int MENU_MAP_TYPE = Menu.FIRST + 1;
	private static final int MENU_MAP_MOVE = Menu.FIRST + 2;
	private static final int MENU_LOCATION_TRACKING = Menu.FIRST + 3;
	private static final int MENU_MAP_OVERLAY = Menu.FIRST + 4;

	private static final String LOG_TAG = "DaumMapLibrarySample";

	private MapView mapView;
	private MapPOIItem poiItem;
	private MapReverseGeoCoder reverseGeoCoder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mapView = (MapView) findViewById(R.id.daumMapView);
		mapView.setDaumMapApiKey(Constants.mapKey);

		mapView.setMapViewEventListener(this);
		mapView.setOpenAPIKeyAuthenticationResultListener(this);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        mapView.setPOIItemEventListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_MAP_TYPE, Menu.NONE, "MapType");
		menu.add(0, MENU_MAP_MOVE, Menu.NONE, "Move");
		menu.add(0, MENU_LOCATION_TRACKING, Menu.NONE, "Location");
		menu.add(0, MENU_MAP_OVERLAY, Menu.NONE, "Overlay");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		final int itemId = item.getItemId();

		switch (itemId) {
		case MENU_MAP_TYPE: {
			String hdMapTile = mapView.isHDMapTileEnabled() ? "HD Map Tile Off"
					: "HD Map Tile On";
			String[] mapTypeMenuItems = { "Standard", "Satellite", "Hybrid",
					hdMapTile, "Clear Map Tile Cache" };

			Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Map Type");
			dialog.setItems(mapTypeMenuItems, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0: // Standard
					{
						mapView.setMapType(MapView.MapType.Standard);
					}
						break;
					case 1: // Satellite
					{
						mapView.setMapType(MapView.MapType.Satellite);
					}
						break;
					case 2: // Hybrid
					{
						mapView.setMapType(MapView.MapType.Hybrid);
					}
						break;
					case 3: // HD Map Tile On/Off
					{
						if (mapView.isHDMapTileEnabled()) {
							mapView.setHDMapTileEnabled(false);
						} else {
							mapView.setHDMapTileEnabled(true);
						}
					}
						break;
					case 4: // Clear Map Tile Cache
					{
						MapView.clearMapTilePersistentCache();
					}
						break;
					}
				}

			});
			dialog.show();
		}
			return true;

		case MENU_MAP_MOVE: {
			String rotateMapMenu = mapView.getMapRotationAngle() == 0.0f ? "Rotate Map 60"
					: "Unrotate Map";
			String[] mapMoveMenuItems = { "Move to", "Zoom to",
					"Move and Zoom to", "Zoom In", "Zoom Out", rotateMapMenu };
			Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Map Type");
			dialog.setItems(mapMoveMenuItems, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0: // Move to
					{
						mapView.setMapCenterPoint(MapPoint
								.mapPointWithGeoCoord(37.56647, 126.977963),
								true);
					}
						break;
					case 1: // Zoom to
					{
						mapView.setZoomLevel(4, true);
					}
						break;
					case 2: // Move and Zoom to
					{
						mapView.setMapCenterPointAndZoomLevel(
								MapPoint.mapPointWithGeoCoord(33.41, 126.52),
								9, true);
					}
						break;
					case 3: // Zoom In
					{
						mapView.zoomIn(true);
					}
						break;
					case 4: // Zoom Out
					{
						mapView.zoomOut(true);
					}
						break;
					case 5: // Rotate Map 60, Unrotate Map
					{
						if (mapView.getMapRotationAngle() == 0.0f) {
							mapView.setMapRotationAngle(60.0f, true);
						} else {
							mapView.setMapRotationAngle(0.0f, true);
						}
					}
						break;
					}
				}

			});
			dialog.show();
		}
			return true;

		case MENU_LOCATION_TRACKING: {
			String[] mapMoveMenuItems = { "User Location On",
					"User Location+Heading On", "Off", "Show Location Marker",
					"Hide Location Marker", "Reverse Geo-coding" };
			Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Location Tracking");
			dialog.setItems(mapMoveMenuItems, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0: // User Location On
					{
						mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
					}
						break;
					case 1: // User Location+Heading On
					{
						mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
					}
						break;
					case 2: // Off
					{
						mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
					}
						break;
					case 3: // Show Location Marker
					{
						mapView.setShowCurrentLocationMarker(true);
					}
						break;
					case 4: // Hide Location Marker
					{
						if (mapView.isShowingCurrentLocationMarker()) {
							mapView.setShowCurrentLocationMarker(false);
						}
					}
						break;
					case 5: // Reverse Geo-coding
					{
						reverseGeoCoder = new MapReverseGeoCoder(
								Constants.mapKey, mapView.getMapCenterPoint(),
								MainActivity.this, MainActivity.this);
						reverseGeoCoder.startFindingAddress();
					}
						break;
					}
				}

			});
			dialog.show();
		}
			return true;

		case MENU_MAP_OVERLAY: {
			String[] overlayMenuItems = { "Add POI Items", "Remove a POI Item",
					"Remove All POI Items", "Add Polyline1", "Add Polyline2",
					"Remove All Polylines" };
			Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Overlay");
			dialog.setItems(overlayMenuItems, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0: // Add POI Items
					{
						mapView.removeAllPOIItems();

						poiItem = new MapPOIItem();
						poiItem.setItemName("City on a Hill");
						poiItem.setMapPoint(MapPoint.mapPointWithGeoCoord(
								37.541889, 127.095388));
						poiItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
						poiItem.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven);
						mapView.addPOIItem(poiItem);

						MapPOIItem poiItem2 = new MapPOIItem();
						poiItem2.setItemName("압구정");
						poiItem2.setMapPoint(MapPoint.mapPointWithGeoCoord(
								37.527896, 127.036245));
						poiItem2.setMarkerType(MapPOIItem.MarkerType.RedPin);
						poiItem2.setShowAnimationType(MapPOIItem.ShowAnimationType.NoAnimation);
						poiItem2.setShowCalloutBalloonOnTouch(true);
						poiItem2.setTag(153);
						mapView.addPOIItem(poiItem2);

						MapPOIItem poiItem3 = new MapPOIItem();
						poiItem3.setItemName("다음커뮤니케이션");
						poiItem3.setUserObject(String.format("item%d", 3));
						poiItem3.setMapPoint(MapPoint.mapPointWithGeoCoord(
								37.537229, 127.005515));
						poiItem3.setMarkerType(MapPOIItem.MarkerType.CustomImage);
						poiItem3.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
						poiItem3.setCustomImageResourceId(R.drawable.custom_poi_marker);
						// if anchor point is not set, the bottom-center point
						// of image will be set as anchor point
						poiItem3.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(
								22, 0));
						mapView.addPOIItem(poiItem3);

						MapPOIItem poiItem4 = new MapPOIItem();
						poiItem4.setItemName("서울숲");
						poiItem4.setMapPoint(MapPoint.mapPointWithGeoCoord(
								37.545024, 127.03923));
						poiItem4.setMarkerType(MapPOIItem.MarkerType.YellowPin);
						poiItem4.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
						poiItem4.setShowDisclosureButtonOnCalloutBalloon(false);
						poiItem4.setDraggable(true);
						poiItem4.setTag(276);
						mapView.addPOIItem(poiItem4);

						mapView.fitMapViewAreaToShowAllPOIItems();
					}
						break;
					case 1: // Remove a POI Item"
					{
						MapPOIItem poiItem = mapView.findPOIItemByTag(276);
						if (poiItem != null) {
							mapView.removePOIItem(poiItem);
						}
					}
						break;
					case 2: // Remove All POI Items
					{
						mapView.removeAllPOIItems();
					}
						break;
					case 3: // Add Polyline1
					{
						MapPolyline existingPolyline = mapView
								.findPolylineByTag(1000);
						if (existingPolyline != null) {
							mapView.removePolyline(existingPolyline);
						}

						MapPolyline polyline1 = new MapPolyline();
						polyline1.setTag(1000);
						polyline1.setLineColor(Color.argb(128, 255, 51, 0));
						polyline1.addPoint(MapPoint.mapPointWithGeoCoord(
								37.537229, 127.005515));
						polyline1.addPoint(MapPoint.mapPointWithGeoCoord(
								37.545024, 127.03923));
						polyline1.addPoint(MapPoint.mapPointWithGeoCoord(
								37.527896, 127.036245));
						polyline1.addPoint(MapPoint.mapPointWithGeoCoord(
								37.541889, 127.095388));
						mapView.addPolyline(polyline1);

						mapView.fitMapViewAreaToShowPolyline(polyline1);
					}
						break;
					case 4: // Add Polyline2
					{
						MapPOIItem existingPOIItemStart = mapView
								.findPOIItemByTag(10001);
						if (existingPOIItemStart != null) {
							mapView.removePOIItem(existingPOIItemStart);
						}

						MapPOIItem existingPOIItemEnd = mapView
								.findPOIItemByTag(10002);
						if (existingPOIItemEnd != null) {
							mapView.removePOIItem(existingPOIItemEnd);
						}

						MapPolyline existingPolyline = mapView
								.findPolylineByTag(2000);
						if (existingPolyline != null) {
							mapView.removePolyline(existingPolyline);
						}

						MapPOIItem poiItemStart = new MapPOIItem();
						poiItemStart.setItemName("Start");
						poiItemStart.setTag(10001);
						poiItemStart.setMapPoint(MapPoint
								.mapPointWithWCONGCoord(475334.0, 1101210.0));
						poiItemStart
								.setMarkerType(MapPOIItem.MarkerType.CustomImage);
						poiItemStart
								.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
						poiItemStart.setShowCalloutBalloonOnTouch(false);
						poiItemStart
								.setCustomImageResourceId(R.drawable.custom_poi_marker_start);
						poiItemStart
								.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(
										29, 2));
						mapView.addPOIItem(poiItemStart);

						MapPOIItem poiItemEnd = new MapPOIItem();
						poiItemEnd.setItemName("End");
						poiItemEnd.setTag(10001);
						poiItemEnd.setMapPoint(MapPoint.mapPointWithWCONGCoord(
								485016.0, 1118034.0));
						poiItemEnd
								.setMarkerType(MapPOIItem.MarkerType.CustomImage);
						poiItemEnd
								.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
						poiItemEnd.setShowCalloutBalloonOnTouch(false);
						poiItemEnd
								.setCustomImageResourceId(R.drawable.custom_poi_marker_end);
						poiItemEnd
								.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(
										29, 2));
						mapView.addPOIItem(poiItemEnd);

						MapPolyline polyline2 = new MapPolyline(21);
						polyline2.setTag(2000);
						polyline2.setLineColor(Color.argb(128, 0, 0, 255));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								475334.0, 1101210.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								474300.0, 1104123.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								474300.0, 1104123.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								473873.0, 1105377.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								473302.0, 1107097.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								473126.0, 1109606.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								473063.0, 1110548.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								473435.0, 1111020.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								474068.0, 1111714.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								475475.0, 1112765.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								476938.0, 1113532.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								478725.0, 1114391.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								479453.0, 1114785.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								480145.0, 1115145.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								481280.0, 1115237.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								481777.0, 1115164.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								482322.0, 1115923.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								482832.0, 1116322.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								483384.0, 1116754.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								484401.0, 1117547.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								484893.0, 1117930.0));
						polyline2.addPoint(MapPoint.mapPointWithWCONGCoord(
								485016.0, 1118034.0));
						mapView.addPolyline(polyline2);

						mapView.fitMapViewAreaToShowAllPolylines();
					}
						break;
					case 5: // Remove All Polylines
					{
						mapView.removeAllPolylines();
					}
						break;
					}
				}

			});
			dialog.show();
		}
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	// net.daum.mf.map.api.MapView.OpenAPIKeyAuthenticationResultListener

	@Override
	public void onDaumMapOpenAPIKeyAuthenticationResult(MapView mapView,
			int resultCode, String resultMessage) {
		Log.i(LOG_TAG, String.format(
				"Open API Key Authentication Result : code=%d, message=%s",
				resultCode, resultMessage));
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	// net.daum.mf.map.api.MapView.MapViewEventListener

	public void onMapViewInitialized(MapView mapView) {
		Log.i(LOG_TAG,
				"MapView had loaded. Now, MapView APIs could be called safely");
		// mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
		mapView.setMapCenterPoint(
				MapPoint.mapPointWithGeoCoord(37.56647, 126.977963), true);
		mapView.setZoomLevel(7, true);

		MapPOIItem poiItem2 = new MapPOIItem();
		poiItem2.setItemName("시청 부근 이벤트정보");
		poiItem2.setUserObject(String.format("item%d", 2));
		poiItem2.setMapPoint(MapPoint
				.mapPointWithGeoCoord(37.56647, 126.977963));
		poiItem2.setMarkerType(MapPOIItem.MarkerType.CustomImage);
		poiItem2.setShowAnimationType(MapPOIItem.ShowAnimationType.DropFromHeaven);
		poiItem2.setCustomImageResourceId(R.drawable.custom_poi_marker_start);
		poiItem2.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(22,
				0));
		poiItem2.setShowCalloutBalloonOnTouch(true);
		mapView.addPOIItem(poiItem2);
	}

	@Override
	public void onMapViewCenterPointMoved(MapView mapView,
			MapPoint mapCenterPoint) {
		MapPoint.GeoCoordinate mapPointGeo = mapCenterPoint
				.getMapPointGeoCoord();
		Log.i(LOG_TAG, String.format(
				"MapView onMapViewCenterPointMoved (%f,%f)",
				mapPointGeo.latitude, mapPointGeo.longitude));
	}

	@Override
	public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

		MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage(String.format("Double-Tap on (%f,%f)",
				mapPointGeo.latitude, mapPointGeo.longitude));
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();
	}

	@Override
	public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

		MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage(String.format("Long-Press on (%f,%f)",
				mapPointGeo.latitude, mapPointGeo.longitude));
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();
	}

	@Override
	public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
		MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
		Log.i(LOG_TAG, String.format("MapView onMapViewSingleTapped (%f,%f)",
				mapPointGeo.latitude, mapPointGeo.longitude));
	}

	@Override
	public void onMapViewZoomLevelChanged(MapView mapView, int zoomLevel) {
		Log.i(LOG_TAG, String.format("MapView onMapViewZoomLevelChanged (%d)",
				zoomLevel));
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	// net.daum.mf.map.api.MapView.CurrentLocationEventListener

	@Override
	public void onCurrentLocationUpdate(MapView mapView,
			MapPoint currentLocation, float accuracyInMeters) {
		MapPoint.GeoCoordinate mapPointGeo = currentLocation
				.getMapPointGeoCoord();
		Log.i(LOG_TAG, String.format(
				"MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)",
				mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
	}

	@Override
	public void onCurrentLocationDeviceHeadingUpdate(MapView mapView,
			float headingAngle) {
		Log.i(LOG_TAG,
				String.format(
						"MapView onCurrentLocationDeviceHeadingUpdate: device heading = %f degrees",
						headingAngle));
	}

	@Override
	public void onCurrentLocationUpdateFailed(MapView mapView) {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage("Current Location Update Failed!");
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();
	}

	@Override
	public void onCurrentLocationUpdateCancelled(MapView mapView) {
		Log.i(LOG_TAG, "MapView onCurrentLocationUpdateCancelled");
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	// net.daum.mf.map.api.MapView.POIItemEventListener

	@Override
	public void onPOIItemSelected(MapView mapView, MapPOIItem poiItem) {
		Log.i(LOG_TAG,
				String.format("MapPOIItem(%s) is selected",
						poiItem.getItemName()));
	}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView mapView,
			MapPOIItem poiItem) {

		String alertMessage = null;

		if (poiItem == this.poiItem) {

			alertMessage = "Touched the callout-balloon of item1";

		} else if (poiItem.getTag() == 153) {

			String addressForThisItem = MapReverseGeoCoder
					.findAddressForMapPoint(Constants.mapKey,
							poiItem.getMapPoint());
			alertMessage = String.format(
					"Touched the callout-balloon of item2 (address : %s)",
					addressForThisItem);

		} else if ((poiItem.getUserObject() instanceof String)
				&& poiItem.getUserObject().equals("item3")) {

			// Intent intent = new Intent(this, MapPOIDetailActivity.class);
			// intent.putExtra("POIName", poiItem.getItemName());
			// startActivity(intent);
			// return;

		} else if (poiItem.getTag() == 276) {

			alertMessage = "Touched the callout-balloon of item4";
		}

		// 풍선클릭시 이벤트

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage(alertMessage);
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();
	}

	@Override
	public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem poiItem,
			MapPoint newMapPoint) {

		MapPoint.GeoCoordinate newMapPointGeo = newMapPoint
				.getMapPointGeoCoord();
		String alertMessage = String.format(
				"Draggable MapPOIItem(%s) has moved to new point (%f,%f)",
				poiItem.getItemName(), newMapPointGeo.latitude,
				newMapPointGeo.longitude);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage(alertMessage);
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////
	// net.daum.mf.map.api.MapReverseGeoCoder.ReverseGeoCodingResultListener

	@Override
	public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder rGeoCoder,
			String addressString) {

		String alertMessage = String.format("Center Point Address = [%s]",
				addressString);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage(alertMessage);
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();

		reverseGeoCoder = null;
	}

	@Override
	public void onReverseGeoCoderFailedToFindAddress(
			MapReverseGeoCoder rGeoCoder) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("DaumMapLibrarySample");
		alertDialog.setMessage("Reverse Geo-Coding Failed");
		alertDialog.setPositiveButton("OK", null);
		alertDialog.show();

		reverseGeoCoder = null;
	}

}
