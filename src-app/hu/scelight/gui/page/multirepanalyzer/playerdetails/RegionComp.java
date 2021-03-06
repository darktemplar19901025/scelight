/*
 * Project Scelight
 * 
 * Copyright (c) 2013 Andras Belicza <iczaaa@gmail.com>
 * 
 * This software is the property of Andras Belicza.
 * Copying, modifying, distributing, refactoring without the author's permission
 * is prohibited and protected by Law.
 */
package hu.scelight.gui.page.multirepanalyzer.playerdetails;

import hu.scelight.gui.page.multirepanalyzer.model.Game;
import hu.scelight.gui.page.multirepanalyzer.model.GeneralStats;
import hu.scelight.gui.page.multirepanalyzer.model.Part;
import hu.scelight.gui.page.multirepanalyzer.model.PlayerStats;
import hu.scelight.sc2.rep.model.initdata.gamedesc.Region;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * Region component.
 * 
 * @author Andras Belicza
 */
@SuppressWarnings( "serial" )
public class RegionComp extends GeneralTableStatsComp< Region, Region > {
	
	/**
	 * Creates a new {@link RegionComp}.
	 * 
	 * @param displayName display name to be used when replays of stats rows are opened
	 * @param playerStats player stats to show region stats for
	 */
	public RegionComp( final String displayName, final PlayerStats playerStats ) {
		super( displayName );
		
		buildGui( playerStats, "<html><br>Region</html>", Region.class );
	}
	
	@Override
	protected Collection< GeneralStats< Region > > calculateStats( final PlayerStats playerStats ) {
		// Calculate stats
		final Map< Region, GeneralStats< Region > > generalStatsMap = new EnumMap<>( Region.class );
		for ( final Game g : playerStats.gameList )
			for ( final Part refPart : g.parts ) { // Have to go through all participants as the zero-toon might be present multiple times
				if ( !playerStats.obj.equals( refPart.toon ) )
					continue;
				
				GeneralStats< Region > gs = generalStatsMap.get( g.region );
				if ( gs == null )
					generalStatsMap.put( g.region, gs = new GeneralStats<>( g.region ) );
				gs.updateWithPartGame( refPart, g );
			}
		
		return generalStatsMap.values();
	}
	
}
