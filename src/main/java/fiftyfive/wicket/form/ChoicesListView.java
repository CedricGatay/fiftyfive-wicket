/**
 * Copyright 2010 55 Minutes (http://www.55minutes.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fiftyfive.wicket.form;

import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;

/**
 * TODO
 * @since 2.0
 */
public abstract class ChoicesListView<T> extends ListView<T>
{
    private IChoiceRenderer<? super T> _choiceRenderer;
    
    public ChoicesListView(String id,
                           IModel<? extends List<? extends T>> choices,
                           IChoiceRenderer<? super T> renderer)
    {
        super(id, choices);
        _choiceRenderer = renderer;
    }
    
    public IChoiceRenderer<? super T> getChoiceRenderer()
    {
        return _choiceRenderer;
    }
    
    protected String getChoiceLabel(T choice)
    {
        // This code was copied from Wicket's CheckBoxMultipleChoice.java
        Object displayValue = getChoiceRenderer().getDisplayValue(choice);
        Class<?> objectClass = displayValue == null ? null : displayValue.getClass();
        // Get label for choice
        String label = "";
        if (objectClass != null && objectClass != String.class)
        {
            IConverter converter = getConverter(objectClass);
            label = converter.convertToString(displayValue, getLocale());
        }
        else if (displayValue != null)
        {
            label = displayValue.toString();
        }
        return label;
    }
    
    protected String getChoiceValue(T choice, int index)
    {
        return getChoiceRenderer().getIdValue(choice, index);
    }
}