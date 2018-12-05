package com.urlrevision.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomDispatcherFilter implements Filter {

	private Map< String, String > mappings = new HashMap<>();
	
	@Override
	public void init( FilterConfig config ) throws ServletException {
		String customDispatcher = config.getInitParameter( "custom-dispatcher-forward" );
		
		if ( customDispatcher == null || (customDispatcher = customDispatcher.trim() ).isEmpty() )
			throw new IllegalStateException( "No proper value provided for 'custom-dispatcher-forward'." );
		
		for ( String line : customDispatcher.split( "\\n" ) ) {
			String[] elements = line.split( "=>" );
			if ( elements.length != 2 ) throw new IllegalStateException( "Invalid pair value; line=[" + line + "]" );
			mappings.put( elements[0].trim(), elements[1].trim() );
		}
	}
	
	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException {
		String url = ( ( HttpServletRequest ) req ).getRequestURL().toString();
		for ( String pattern : mappings.keySet() )
			if ( Pattern.compile( pattern ).matcher( url ).matches() ) {
				req.getRequestDispatcher( url.replaceAll( pattern, mappings.get( pattern ) ) ).forward( req, resp );
				return;
			}
		chain.doFilter( req, resp );
	}

	@Override
	public void destroy() {
		this.mappings = null;
	}
	
}
