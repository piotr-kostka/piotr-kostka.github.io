package com.kodilla.rental_frontend.view;

import com.kodilla.rental_frontend.domain.Model;
import com.kodilla.rental_frontend.form.ModelForm;
import com.kodilla.rental_frontend.service.ModelService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "models", layout = MainLayout.class)
@PageTitle("Models | Rent APP")
public class ModelView extends VerticalLayout {

    private final ModelService modelService = ModelService.getInstance();
    private final Grid<Model> grid = new Grid<>(Model.class);
    private final TextField filter = new TextField();
    private final ModelForm form = new ModelForm(this);

    public ModelView() {
        filter.setPlaceholder("Filter by model");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> updateModel());

        grid.setColumns("manufacturer", "name", "engineSize", "bodyType", "productionYear", "color", "seatsQuantity", "doorQuantity", "fuelType", "transmissionType");

        Button addNewModel = new Button("Add new model");
        addNewModel.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setModel(new Model());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filter, addNewModel);
        HorizontalLayout userContent = new HorizontalLayout(grid, form);
        userContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, userContent);
        form.setModel(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setModel(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(modelService.getModels());
    }

    private void updateModel() {
        grid.setItems(modelService.findByName(filter.getValue()));
    }

}
