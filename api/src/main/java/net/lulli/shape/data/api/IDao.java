package net.lulli.shape.data.api;

import net.lulli.shape.data.api.roles.IDaoAdmin;
import net.lulli.shape.data.api.roles.IDaoRead;
import net.lulli.shape.data.api.roles.IDaoWrite;

public interface IDao<T> extends IDaoAdmin<T>, IDaoRead<T>, IDaoWrite<T> {}
