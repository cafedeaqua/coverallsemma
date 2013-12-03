/* Copyright (C) 2003 Vladimir Roubtsov. All rights reserved.
 * 
 * This program and the accompanying materials are made available under
 * the terms of the Common Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * $Id: DeclaredExceptionTable.java,v 1.1.1.1 2004/05/09 16:57:47 vlad_r Exp $
 */
package com.vladium.jcd.cls.attribute;

import java.io.IOException;
import java.util.Vector;

import com.sun.org.apache.xml.internal.utils.IntVector;
import com.vladium.jcd.lib.UDataOutputStream;

// ----------------------------------------------------------------------------
/**
 * @author (C) 2001, Vlad Roubtsov
 */
final class DeclaredExceptionTable implements IDeclaredExceptionTable
{
    // public: ................................................................

    // ACCESSORS:

    @Override
	public int get (final int offset)
    {
        return m_exceptions.get (offset);
    }
    
    @Override
	public int size ()
    {
        return m_exceptions.size ();
    }
    
    @Override
	public long length ()
    {
        return (1 + m_exceptions.size ()) << 1; // use size() if class becomes non-final
    }
        
    // Cloneable:
    
    /**
     * Performs a deep copy.
     */
    @Override
	public Object clone ()
    {
        try
        {
            final DeclaredExceptionTable _clone = (DeclaredExceptionTable) super.clone ();
            
            // deep clone:
            _clone.m_exceptions = (Vector<Integer>) m_exceptions.clone ();
            
            return _clone;
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError (e.toString ());
        }        
    }
    
    // IClassFormatOutput:
    
    @Override
	public void writeInClassFormat (final UDataOutputStream out) throws IOException
    {
        int number_of_exceptions = m_exceptions.size (); // use size() if class becomes non-final
        out.writeU2 (number_of_exceptions);
        
        for (int i = 0; i < number_of_exceptions; i++)
        {
            out.writeU2 (get (i));
        }
    }


    // MUTATORS:
        
    @Override
	public int add (final int exception_index)
    {
        final int newoffset = m_exceptions.size (); // use size() if class becomes non-final
        m_exceptions.add (exception_index);
        
        return newoffset;
    }
    
    @Override
	public int set (final int offset, final int exception_index)
    {
        return m_exceptions.set (offset, exception_index);
    }
    
    // protected: .............................................................

    // package: ...............................................................


    DeclaredExceptionTable (final int capacity)
    {
         m_exceptions = capacity < 0 ? new Vector<Integer> () : new Vector<Integer> (capacity);
    }

    // private: ...............................................................

    
    private Vector<Integer> m_exceptions;
    
} // end of class
// ----------------------------------------------------------------------------
