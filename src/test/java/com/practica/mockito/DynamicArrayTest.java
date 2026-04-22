package com.practica.mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
public class DynamicArrayTest {

    @Mock
    private Consumer<ElementoPrueba> mockConsumer;

    @Spy
    private DynamicArray<ElementoPrueba> spyArray = new DynamicArray<>(2);

    @Captor
    private ArgumentCaptor<ElementoPrueba> elementCaptor;

    interface ElementoPrueba {
        String ejecutar();
    }

    @Test
    void testStep4_MockCreation() {
        ElementoPrueba mockElement = mock(ElementoPrueba.class);
        assertNotNull(mockElement);
    }

    @Test
    void testStep5_2_Verification() {
        ElementoPrueba e = mock(ElementoPrueba.class);
        spyArray.add(e);
        verify(spyArray).add(e);
    }

    @Test
    void testStep5_3_Exceptions() {
        Iterator<ElementoPrueba> it = spyArray.iterator();
        assertThrows(NoSuchElementException.class, () -> it.next());
    }

    @Test
    void testStep6_1_WhenThenReturn() {
        ElementoPrueba e = mock(ElementoPrueba.class);
        when(e.ejecutar()).thenReturn("Procesado");
        assertEquals("Procesado", e.ejecutar());
    }

    @Test
    void testStep6_2_DoReturnWhen() {
        doReturn(100).when(spyArray).getSize();
        assertEquals(100, spyArray.getSize());
    }

    @Test
    void testStep7_Spy() {
        spyArray.add(mock(ElementoPrueba.class));
        assertEquals(1, spyArray.getSize());
        verify(spyArray).add(any());
    }

    @Test
    void testStep9_ArgumentCaptor() {
        ElementoPrueba e = mock(ElementoPrueba.class);
        spyArray.add(e);
        
        Iterator<ElementoPrueba> it = spyArray.iterator();
        it.forEachRemaining(mockConsumer);

        verify(mockConsumer).accept(elementCaptor.capture());
        assertEquals(e, elementCaptor.getValue());
    }

    @Test
    void testStep10_Answers() {
        ElementoPrueba e = mock(ElementoPrueba.class);
        when(e.ejecutar()).thenAnswer(new Answer<String>() {
            private int count = 0;
            @Override
            public String answer(InvocationOnMock invocation) {
                return "Ejecución:" + (++count);
            }
        });

        assertEquals("Ejecución:1", e.ejecutar());
        assertEquals("Ejecución:2", e.ejecutar());
    }
}