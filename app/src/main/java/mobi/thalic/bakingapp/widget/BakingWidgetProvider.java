package mobi.thalic.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import mobi.thalic.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {


    /**
     * Method to update all baking widgets
     * @param context to use
     * @param appWidgetManager to use
     * @param appWidgetIds of all widgets
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update all widgets
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
            // create the intent
            Intent intent = new Intent(context, StackWidgetService.class);
            // add the app widget id
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            // set remote adapter to stack view
            views.setRemoteAdapter(R.id.sv_widget_stack_view, intent);
            // set empty view
            views.setEmptyView(R.id.sv_widget_stack_view, R.id.tv_widget_empty_view);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    // Required overrides with default instructions for future implementation
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

