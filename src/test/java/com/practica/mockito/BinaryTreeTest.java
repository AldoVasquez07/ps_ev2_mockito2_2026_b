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

@ExtendWith(MockitoExtension.class)
public class BinaryTreeTest {

    @Mock
    private BinaryTree.Node mockNode;

    @Spy
    private BinaryTree spyTree;

    @Captor
    private ArgumentCaptor<Integer> valueCaptor;

    @Test
    void testStep4_MockCreation() {
        assertNotNull(mockNode);
        BinaryTree tree = new BinaryTree(mockNode);
        assertNotNull(tree.getRoot());
    }

    @Test
    void testStep5_1_Dependencies() {
        BinaryTree tree = new BinaryTree();
        tree.put(10);
        assertNotNull(tree.find(10));
    }

    @Test
    void testStep5_2_Verification() {
        spyTree.put(50);
        verify(spyTree).put(50);
    }

    @Test
    void testStep5_3_Exceptions() {
        doThrow(new IllegalArgumentException()).when(spyTree).put(-1);
        assertThrows(IllegalArgumentException.class, () -> spyTree.put(-1));
    }

    @Test
    void testStep6_1_WhenThenReturn() {
        BinaryTree treeMock = mock(BinaryTree.class);
        when(treeMock.find(10)).thenReturn(mockNode);
        assertEquals(mockNode, treeMock.find(10));
    }

    @Test
    void testStep6_2_DoReturnWhen() {
        doReturn(mockNode).when(spyTree).getRoot();
        assertEquals(mockNode, spyTree.getRoot());
    }

    @Test
    void testStep7_Spy() {
        spyTree.put(10);
        spyTree.put(20);
        verify(spyTree, times(2)).put(anyInt());
        assertNotNull(spyTree.getRoot());
    }

    @Test
    void testStep9_ArgumentCaptor() {
        spyTree.put(75);
        verify(spyTree).put(valueCaptor.capture());
        assertEquals(75, valueCaptor.getValue());
    }

    @Test
    void testStep10_Answers() {
        when(mockNode.toString()).thenAnswer(new Answer<String>() {
            private int count = 0;
            @Override
            public String answer(InvocationOnMock invocation) {
                return "NodeCall:" + (++count);
            }
        });

        assertEquals("NodeCall:1", mockNode.toString());
        assertEquals("NodeCall:2", mockNode.toString());
    }
}